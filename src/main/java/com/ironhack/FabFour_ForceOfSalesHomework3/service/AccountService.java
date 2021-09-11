package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.isDuplicateAccount;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.getUserInput;

@Service
public class AccountService {

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

    // Print out Account details
    public static void lookUpAccount(long id)  {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isEmpty()) {
            colorMessage("There is no Account with id " + id + ". Please try again.", TextColor.RED);
        } else {
            Account account = accountOptional.get();
            System.out.println(account);
        }
    }

    // Obtain Account data from user input
    public static List<Object> getAccountData() {
        int employees = 0;
        System.out.println("Please provide the industry name.\nPossible choices are: PRODUCE, ECOMMERCE, MANUFACTURING, MEDICAL, OTHER");
        Industry industry = (Industry) getUserInput("industry");
        System.out.println("Please provide the number of company employees");
        try {
            employees = (Integer) getUserInput("employees");
        } catch(NullPointerException e) { System.out.println("Something went wrong."); }
        System.out.println("Please provide the city name");
        String city = (String) getUserInput("city");
        System.out.println("Please provide the full country name (e.g. United Kingdom, Germany)");
        String country = (String) getUserInput("country");
        return Arrays.asList(industry, employees, city, country);
    }

    // Create new Account
    public static Account createAccount(LeadObject lead, Contact contact, Opportunity opportunity, List<Object> dataList) {
        Account account;
        List<Contact> contactList = new ArrayList<>();
        List<Opportunity> opportunityList = new ArrayList<>();
        contactList.add(contact);
        opportunityList.add(opportunity);
        account = new Account((Industry) dataList.get(0), (Integer) dataList.get(1), WordUtils.capitalizeFully((String)dataList.get(2)),
                WordUtils.capitalizeFully((String) dataList.get(3)), contactList, opportunityList);
        if(!isDuplicateAccount(account)) {
            accountRepository.save(account);
            contact.setAccount(account);
            opportunity.setAccount(account);
            contactRepository.save(contact);
            opportunityRepository.save(opportunity);
            leadObjectRepository.deleteById(lead.getId());
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            colorMessage("Opportunity created. Opportunity ID: " + opportunity.getId(), TextColor.GREEN);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            colorMessage("Account created. Account ID: " + account.getId(), TextColor.GREEN);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            return account;
        } else {
            System.out.println("Account with this information already exists.");
            showAccounts();
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            colorMessage("Terminating convert operation. Your data will not be saved.", TextColor.GREEN);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            return null;
        }
    }

    // Add new Contact and Opportunity to existing Account
    public static Account addToAccount(String id, LeadObject lead, Contact contact, Opportunity opportunity) {
        Long accountId = Long.parseLong(id);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        Account account = accountOptional.orElse(null);
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
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
        colorMessage("Account " + account.getId() + " has been updated", TextColor.GREEN);
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
        return account;
        }

    // Obtain id of the Account to be used
    public static List<Object> getAccountId() {
        System.out.println("Please provide the id of the Account you want to use or type 'go back' to abandon the operation.");
        Object accountId =  getUserInput("accountId");
        if((accountRepository.count() == 0) || Objects.equals(accountId, "go back")) {
            showAccounts();
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            colorMessage("Terminating convert operation. Your data will not be saved.", TextColor.GREEN);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            removeIncompleteData();
            return Arrays.asList(0);
        } else if(Objects.equals(accountId, "bad input") || Objects.equals(accountId, "no account")){ return getAccountId(); }
        return Arrays.asList(accountId);
    }

    // Remove data from database if the lead conversion is terminated
    public static void removeIncompleteData() {
        for(Opportunity o : opportunityRepository.findAll()) {
            if(o.getAccount() == null) { opportunityRepository.deleteById(o.getId()); }
        }
        for(Contact c: contactRepository.findAll()) {
            if(c.getAccount() == null) { contactRepository.deleteById(c.getId()); }
        }
    }

    // Obtain list of ISO country names
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

    // Print out information on all existing Accounts
    public static void showAccounts() {
        List<Account> accountList = accountRepository.findAll();
        String printFormat = "| %-10d | %-20s | %-15s | %-35s | %-19s| %n";

        if(accountList.size() > 0) {
            System.out.println("These are the Accounts logged in our system:" + "\n");
            System.out.format("+------------+----------------------+-----------------+--------------------------------------------+--------------------+%n");
            System.out.format("| ID         | Industry             | City            | Country                                    | Number of Contacts |%n");
            System.out.format("+------------+----------------------+-----------------+--------------------------------------------+--------------------+%n");
            for (Account account : accountList) {
                System.out.format(printFormat, account.getId(), account.getIndustry(), account.getCity(), account.getCountry(), account.getContactList().size());
            }
            System.out.format("+------------+----------------------+-----------------+--------------------------------------------+--------------------+%n");
        } else System.out.println("There are no Accounts! Try to add a new one by typing 'convert {lead id}'.");
    }
}
