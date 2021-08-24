package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.getUserInput;

@Service
public class AccountService {

    public static final String RED_TEXT = "\033[31m";
    public static final String GREEN_TEXT = "\u001B[32m";

    private static AccountRepository accountRepository;
    private static ContactRepository contactRepository;
    private static OpportunityRepository opportunityRepository;
    private static LeadObjectRepository leadObjectRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          ContactRepository contactRepository,
                          OpportunityRepository opportunityRepository,
                          LeadObjectRepository leadObjectRepository) {
        this.accountRepository = accountRepository;
        this.contactRepository = contactRepository;
        this.opportunityRepository = opportunityRepository;
        this.leadObjectRepository = leadObjectRepository;
    }

    public static Account lookUpAccount(long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        Account account = accountOptional.get();
        if(account == null) {
            colorMessage("There is no account with id "+id, RED_TEXT);
        }
        return account;
    }

    //getAccountData
    public static List<Object> getAccountData() {
        System.out.println("Please provide the industry name.\nPossible choices are: PRODUCE, ECOMMERCE, MANUFACTURING, MEDICAL, OTHER");
        Industry industry = (Industry) getUserInput("industry");
        System.out.println("Please provide the number of company employees");
        int employees  = (Integer) getUserInput("employees");
        System.out.println("Please provide the city name");
        String city = (String) getUserInput("city");
        System.out.println("Please provide the full country name (e.g. United Kingdom, Germany)");
        String country = (String) getUserInput("country");
        return Arrays.asList(industry, employees, city, country);
    }
    //createAccount
    public static Account createAccount(LeadObject lead, Contact contact, Opportunity opportunity, List<Object> dataList) {
        Account account;
        List<Contact> contactList = new ArrayList<>();
        List<Opportunity> opportunityList = new ArrayList<>();
        contactList.add(contact);
        opportunityList.add(opportunity);
        account = new Account((Industry) dataList.get(0), (Integer) dataList.get(1), WordUtils.capitalizeFully((String)dataList.get(2)),
                WordUtils.capitalizeFully((String) dataList.get(3)), contactList, opportunityList);
        accountRepository.save(account);
        contact.setAccount(account);
        opportunity.setAccount(account);
        contactRepository.save(contact);
        opportunityRepository.save(opportunity);
        leadObjectRepository.deleteById(lead.getId());
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        colorMessage("Opportunity created. Opportunity ID: " + opportunity.getId(), GREEN_TEXT);
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        colorMessage("Account created. Account ID: " + account.getId(), GREEN_TEXT);
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        return account;
    }
    //addToAccount
    public static Account addToAccount(String id, LeadObject lead, Contact contact, Opportunity opportunity) {
        Long accountId = Long.parseLong(id);
        Account account = lookUpAccount(accountId);
        contact.setAccount(account);
        contactRepository.save(contact);
        opportunity.setAccount(account);
        opportunityRepository.save(opportunity);
        List<Contact> newList = account.getContactList();
        List<Opportunity> newOpp = account.getOpportunityList();
        newList.add(contact);
        newOpp.add(opportunity);
        account.setContactList(newList);
        account.setOpportunityList(newOpp);
        accountRepository.save(account);
        leadObjectRepository.deleteById(lead.getId());
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        colorMessage("Account " + account.getId() + " has been updated", GREEN_TEXT);
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        return account;
    }
    //getAccountId
    public static List<Object> getAccountId() {
        System.out.println("Please provide the id of the Account you want to use");
        String accountId = (String) getUserInput("accountId");
        return Arrays.asList(accountId);
    }

    public static List<String> getCountryList() {
        List<String> countries = new ArrayList<>();
        Locale.setDefault(Locale.forLanguageTag("en-GB")); //set Locale for English
        String[] isoCountries = Locale.getISOCountries(); //obtain ISO country list
        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            String name = locale.getDisplayCountry();
            if ( !"".equals(name)) {
                countries.add(name); //store country name in list
            }
        }
        return countries;
    }

}
