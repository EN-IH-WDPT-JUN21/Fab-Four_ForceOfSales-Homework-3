package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.apache.commons.lang.WordUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.getCountryList;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.SalesRepService.newSalesRep;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.SalesRepService.showSalesReps;

@Service
public class DataValidatorService {

    private static LeadObjectRepository leadObjectRepository;
    private static OpportunityRepository opportunityRepository;
    private static SalesRepRepository salesRepRepository;
    private static AccountRepository accountRepository;


    public DataValidatorService(LeadObjectRepository leadObjectRepository,
                                OpportunityRepository opportunityRepository,
                                SalesRepRepository salesRepRepository,
                                AccountRepository accountRepository) {
        this.leadObjectRepository = leadObjectRepository;
        this.opportunityRepository = opportunityRepository;
        this.salesRepRepository = salesRepRepository;
        this.accountRepository = accountRepository;
    }

    //Method to check if the input for the e-mail address has a correct form
    public static boolean validateEmail(String input) {
        final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    //Method to check if the input for the phone number has a correct form
    public static boolean validatePhoneNumber(String input) {
        String inputWithoutSpaces = input.replaceAll("-", "");
        final String regex = "^\\+?\\d{6,15}"; // Phone number should contain 6-15 digits and can include the country code

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputWithoutSpaces);
        return matcher.matches();
    }

    //Method to check if the input is empty
    public static boolean isEmpty(String input) {
        return input.isBlank();
    }

    //Method to check if a lead with a specific ID exists
    public static boolean leadExists(String input) {
        List<LeadObject> listOfLeadObjects = leadObjectRepository.findAll();
        long inputAsLong = Long.parseLong(input);

        for(LeadObject leadObject : listOfLeadObjects) {
            long leadId = leadObject.getId();
            if(leadId == inputAsLong) return true;
        }
        return false;
    }

    //Method to check if an opportunity with the specific ID exists
    public static boolean opportunityExists(String input) {
        List<Opportunity> listOfOpportunities = opportunityRepository.findAll();
        long inputAsLong = Long.parseLong(input);

        for(Opportunity opportunity : listOfOpportunities) {
            long opportunityId = opportunity.getId();
            if(opportunityId == inputAsLong) return true;
        }
        return false;
    }

    //Method to check if an account with the specific ID exists
    public static boolean accountExists(String input) {
        List<Account> listOfAccounts = accountRepository.findAll();
        long inputAsLong = Long.parseLong(input);

        for(Account account : listOfAccounts) {
            long accountId = account.getId();
            if(accountId == inputAsLong) return true;
        }
        return false;
    }

    //Method to check if a salesRep with the specific ID exists
    public static boolean salesRepExists(String input) {
        List<SalesRep> listOfSalesReps = salesRepRepository.findAll();
        long inputAsLong = Long.parseLong(input);

        for(SalesRep salesRep : listOfSalesReps) {
            long salesRepId = salesRep.getId();
            if(salesRepId == inputAsLong) return true;
        }
        return false;
    }

    //Method to check if a lead with the same information already exists
    public static boolean isDuplicateLead(LeadObject newLeadObject) {
        List<LeadObject> listOfLeadObjects = leadObjectRepository.findAll();

        for(LeadObject leadObject : listOfLeadObjects) {
            if(leadObject.equals(newLeadObject)) return true;
        }
        return false;
    }

    //Method to check if an opportunity with the same information already exists
    public static boolean isDuplicateOpportunity(Opportunity inputOpportunity) {
        List<Opportunity> listOfOpportunities = opportunityRepository.findAll();
        for(Opportunity opportunity : listOfOpportunities) {
                if(opportunity.equals(inputOpportunity)) return true;
            }
        return false;
    }

    //Method to check if the String contains only letters and white spaces
    public static boolean containsOnlyLetters(String input) {
        final String regex = "^[ a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    //Method to check if the String is an ISO country name
    public static boolean validateCountryName(String countryName) {
        List<String> countryList = getCountryList();
        boolean check = false;
        for(String country: countryList) {
            if(WordUtils.capitalizeFully(countryName).equals(country)) {
                check = true;
                break;
            }
        }
        return check;
    }

}

