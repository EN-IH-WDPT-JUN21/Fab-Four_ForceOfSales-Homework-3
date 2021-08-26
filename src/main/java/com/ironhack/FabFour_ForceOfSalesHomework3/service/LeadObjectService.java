package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.addToAccount;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.createAccount;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.ContactService.createContact;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.getUserInput;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.OpportunityService.createOpportunity;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.SalesRepService.*;

@Service
public class LeadObjectService {

    public static final String RED_TEXT = "\033[31m";
    public static final String GREEN_TEXT = "\u001B[32m";

    private static LeadObjectRepository leadObjectRepository;
    private static SalesRepRepository salesRepRepository;

    public LeadObjectService(LeadObjectRepository leadObjectRepository,
                             SalesRepRepository salesRepRepository) {
        this.leadObjectRepository = leadObjectRepository;
        this.salesRepRepository = salesRepRepository;
    }

    public static LeadObject createLead() {
        String tempName;
        String tempNumber = null;
        String tempEmail = null;
        String tempCompany = null;
        String tempString; long tempLong;
        LeadObject tempLeadObject = null;
        List<SalesRep> salesList = salesRepRepository.findAll();
        try {
            Scanner aScanner = new Scanner(System.in);
            System.out.println("Please enter your SalesRep id.");
            SalesRep sales = findSalesRep(aScanner.nextLong()); // needs a method that returns a SalesRep or null
            while(sales == null) {
                colorMessage("That id does not exist. Would you like to create a new SalesRep profile? y/n", RED_TEXT);
                if (aScanner.nextLine().equals("y") || aScanner.nextLine().equals("Y")) {
                    newSalesRep();
                }
                else if (salesList.size() < 1){
                    System.out.println("Please create a SalesRep profile.");
                    newSalesRep();
                }
                else {
                    System.out.println("These are the current Sales Reps.");
                    showSalesReps();
                    System.out.println("Please enter a valid SalesRep id.");
                    sales = findSalesRep(aScanner.nextLong());
                }
            }
            System.out.println("Please enter their contact name.");
            tempName = aScanner.nextLine();
            System.out.println("Please enter their phone number, with no spaces.");
            tempString = aScanner.nextLine();
            if (validatePhoneNumber(tempString)) { tempNumber = tempString; }
            else {
                while (tempNumber == null) {
                    colorMessage("Please provide a valid phone number. It must be between 6 and 15 digits, and can have hyphens or +. Spaces are not allowed.",RED_TEXT);
                    tempString = aScanner.nextLine();
                    if (validatePhoneNumber(tempString)) {
                        tempNumber = tempString;
                    }
                }
            }
            System.out.println("Please enter their email address.");
            tempString = aScanner.nextLine();
            if (validateEmail(tempString)) {
                tempEmail = tempString;
            }
            else {
                while (tempEmail == null) {
                    colorMessage("Please provide a valid email address.",RED_TEXT);
                    tempString = aScanner.nextLine();
                    if (validateEmail(tempString)) {
                        tempEmail = tempString;
                    }
                }
            }
            System.out.println("Please enter their company's name");
            tempCompany = aScanner.nextLine();
            tempLeadObject = new LeadObject(tempName, tempNumber, tempEmail, tempCompany, sales);
            leadObjectRepository.save(tempLeadObject);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
            colorMessage("Lead created. Lead ID: " + tempLeadObject.getId(), GREEN_TEXT);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        }
        catch (Exception e) { System.out.println("Exception: " + e); }
        return tempLeadObject;
    }

    public static Account convertLead(long id) {
        //Wrapper method for converting Lead and setting up the Account object
        if (!leadExists(Long.toString(id))) {
            colorMessage("Lead doesn't exist. Please provide the correct id.", RED_TEXT);
            return null;
        } else {
            LeadObject lead = lookupLead(id);
            Contact contact = createContact(lead);
            Opportunity opportunity = createOpportunity(lead, contact);
            System.out.println("Would you like to create a new Account? (Y/N)");
            List<Object> dataList = (List) getUserInput("account");
            Account account = (dataList.size() > 1) ?
                    createAccount(lead, contact, opportunity, dataList) : addToAccount((String)dataList.get(0), lead, contact, opportunity);
            return account;
        }
    }

    public static LeadObject lookupLead(long id) {
        Optional<LeadObject> leadOptional = leadObjectRepository.findById(id);
        LeadObject lead = leadOptional.get();
        if(lead == null) {
            colorMessage("There is no lead with id "+id, RED_TEXT);
        }

        return lead;
    }

    public static void removeLead(long id) {
        leadObjectRepository.deleteById(id);
    }

    public static int countLeads() {
        return leadObjectRepository.findAll().size();
    }

    // Shows attribute information for each lead currently in LeadList. Returns an error if no leads exists.
    public static void showLeads() {
        List<LeadObject> leadList = leadObjectRepository.findAll();
        if(countLeads() > 0) {
            for (LeadObject leadObject : leadList) {
                System.out.println("Lead ID: " + leadObject.getId() + ", Contact Name: " + leadObject.getContactName() + ".\n");
            }
        } else System.out.println("There are no leads! Try to add some with the 'new lead' command");
    }
}
