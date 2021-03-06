package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.ContactService.createContact;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.OpportunityService.createOpportunity;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.SalesRepService.*;

@Service
public class LeadObjectService {

    private static LeadObjectRepository leadObjectRepository;
    private static SalesRepRepository salesRepRepository;

    public LeadObjectService(LeadObjectRepository leadObjectRepository,
                             SalesRepRepository salesRepRepository) {
        this.leadObjectRepository = leadObjectRepository;
        this.salesRepRepository = salesRepRepository;
    }

    public static LeadObject createLead() {
        String tempName; String tempNumber = null;
        String tempEmail = null; String tempCompany;
        String tempString;
        LeadObject tempLeadObject = null; SalesRep sales;
        try {
            Scanner aScanner = new Scanner(System.in);
            System.out.println("Please enter your SalesRep id.");
            sales = validateSalesRepLeadConstructor();
            System.out.println("Please enter the lead's contact name.");
            tempName = aScanner.nextLine();
            while (tempName == null || tempName.equals("")) {
                colorMessage("Contact name cannot be blank. Please try again.", TextColor.RED);
                tempName = aScanner.nextLine();
            }
            while (!containsOnlyLetters(tempName)) {
                colorMessage("Please provide a valid contact name, with only letters and spaces.", TextColor.RED);
                tempName = aScanner.nextLine();
            }
            System.out.println("Please enter their phone number, with no spaces.");
            while (tempNumber == null) {
                tempString = aScanner.nextLine();
                if (validatePhoneNumber(tempString)) { tempNumber = tempString; }
                else {
                    colorMessage("Please provide a valid phone number. It must be between 6 and 15 digits, and can have hyphens or +. Spaces are not allowed.",TextColor.RED);
                }
            }
            System.out.println("Please enter their email address.");
            while (tempEmail == null) {
                tempString = aScanner.nextLine();
                if (validateEmail(tempString)) {
                    tempEmail = tempString;
                }
                else {
                    colorMessage("Please provide a valid email address.",TextColor.RED);
                }
            }
            System.out.println("Please enter their company's name");
            tempCompany = aScanner.nextLine();
            tempLeadObject = new LeadObject(tempName, tempNumber, tempEmail, tempCompany, sales);
            leadObjectRepository.save(tempLeadObject);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
            colorMessage("Lead created. Lead ID: " + tempLeadObject.getId(), TextColor.GREEN);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", TextColor.GREEN);
        }
        catch (Exception e) { System.out.println("Exception: " + e); }
        return tempLeadObject;
    }

    public static SalesRep validateSalesRepLeadConstructor() {
        long tempLong; String tempString;
        SalesRep sales = null;
        List<SalesRep> salesList = salesRepRepository.findAll();
        Scanner aScanner = new Scanner(System.in);
        while (sales == null) {
            tempString = aScanner.nextLine();
            if (isNumeric(tempString)) {
                tempLong = Long.parseLong(tempString);
                if (salesRepExists(tempString)) {
                    Optional<SalesRep> salesRep = salesRepRepository.findById(tempLong);
                    sales = salesRep.get();
                } else if (salesList.size() < 1) {
                    System.out.println("No SalesRep profiles exist. Please create one now.");
                    sales = newSalesRep();
                } else {
                    showSalesReps();
                    System.out.println("If you would like to create a new profile, type y.");
                    tempString = aScanner.nextLine();
                    if (tempString.contains("y") || tempString.contains("Y")) {
                        sales = newSalesRep();
                    }
                    else {
                        System.out.println(colorMessage("Please enter a valid SalesRep id.",TextColor.RED));
                    }
                }
            }
            else {
                System.out.println(colorMessage("SalesRep Id can only be a number. Please try again.",TextColor.RED));
            }
        }
        return sales;
    }

    public static Account convertLead(long id) {
        //Wrapper method for converting Lead and setting up the Account object
        if (!leadExists(Long.toString(id))) {
            colorMessage("Lead doesn't exist. Please provide the correct id.", TextColor.RED);
            return null;
        } else {
            LeadObject lead = lookupLead(id);
            Contact contact = createContact(lead);
            Opportunity opportunity = createOpportunity(lead, contact);
            System.out.println("Would you like to create a new Account? (Y/N)");
            /* List object will be returned, either a one-element List with an Account Id if the user
            * doesn't want to create a new account or a dataList to be used in Account creation.
            * Should the user abandon the convert operation or tries to add the info when no Accounts exists
            * in the system, a List containing a single zero will be returned */
            List<Object> dataList = (List) getUserInput("account");
            if (dataList.get(0).toString().equals("0")) {
                return null;
            } else {
                Account account = (dataList.size() > 1) ?
                        createAccount(lead, contact, opportunity, dataList) : addToAccount(String.valueOf(dataList.get(0)), lead, contact, opportunity);
                return account;
            }
        }
    }

    public static LeadObject lookupLead(long id) {
        Optional<LeadObject> leadOptional = leadObjectRepository.findById(id);
        if (leadOptional.isPresent()) { return leadOptional.get(); }
        else {
            colorMessage("There is no lead with id "+id, TextColor.RED);
            return null;
        }
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
        String leadContactName = null;
        if(countLeads() > 0) {
            String printFormat = "| %-10d | %-25s | %n";
            System.out.printf("Here are the current leads: \n%n");
            System.out.format("+------------+---------------------------+%n");
            System.out.format("| Lead ID    | Contact Name              |%n");
            System.out.format("+------------+---------------------------+%n");
            for (LeadObject leadObject : leadList) {
                leadContactName = (leadObject.getContactName().length() > 10) ?
                        (leadObject.getContactName().substring(0, 20) + "...") : leadObject.getContactName();
                System.out.format(printFormat,leadObject.getId(), leadContactName.toUpperCase());
                System.out.format("+------------+---------------------------+%n");
            }
        } else colorMessage("+--- There are no leads! Try to add some with the 'new lead' command. ---+", TextColor.RED);
    }
}
