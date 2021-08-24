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
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.leadExists;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.getUserInput;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.OpportunityService.createOpportunity;

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
        String tempNumber;
        String tempEmail;
        String tempCompany;
        Long tempSalesId;
        LeadObject tempLeadObject = null;
        try {
            Scanner aScanner = new Scanner(System.in);
            System.out.println("Please enter their contact name.");
            tempName = aScanner.nextLine();
            System.out.println("Please enter their phone number, with no spaces.");
            tempNumber = aScanner.nextLine();
            System.out.println("Please enter their email address.");
            tempEmail = aScanner.nextLine();
            System.out.println("Please enter their company's name");
            tempCompany = aScanner.nextLine();
            System.out.println("Please enter their SalesRep id.");
            tempSalesId = aScanner.nextLong();
            Optional<SalesRep> salesOptional = salesRepRepository.findById(tempSalesId);
            SalesRep sales = salesOptional.get();
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
