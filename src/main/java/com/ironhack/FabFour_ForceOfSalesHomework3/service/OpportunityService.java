package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.getUserInput;

@Service
public class OpportunityService {

    public static final String RED_TEXT = "\033[31m";
    public static final String GREEN_TEXT = "\u001B[32m";

    private static OpportunityRepository opportunityRepository;

    public OpportunityService(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    public static Opportunity createOpportunity(LeadObject lead, Contact contact) {
        //Obtains account data from user input and Contact object
        System.out.println("Please provide the type of the product you're interested in.\nPossible choices are: HYBRID, FLATBED, BOX");
        Product newProduct = (Product) getUserInput("product");
        System.out.println("Please provide the number of trucks you're interested in.\nMaximum amount: 300");
        int quantity = (Integer) getUserInput("quantity");
        Opportunity opportunity = new Opportunity(newProduct, quantity, contact, lead.getSales());
        opportunityRepository.save(opportunity);
        return opportunity;
    }

    public static Opportunity lookUpOpportunity(long id) {
        Optional<Opportunity> opportunityOptional = opportunityRepository.findById(id);
        Opportunity opportunity = opportunityOptional.get();
        if(opportunity == null) {
            colorMessage("There is no opportunity with id "+id, RED_TEXT);
        }

        return opportunity;
    }

    // Update opportunity status to closed-lost
    public static void updateOpportunityStatusClosedLost(long id) {
        boolean found = false;
        Opportunity opportunity = lookUpOpportunity(id);
        if (opportunity != null) {
            opportunity.setStatus(Status.CLOSED_LOST);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
            colorMessage("The opportunity status has been set to 'closed-lost'.", GREEN_TEXT);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
            found = true;
        }
        if(!found) {
            System.out.println("There is no opportunity with this ID. Please try again.");
        }
    }

    // Update opportunity status to closed-won
    public static void updateOpportunityStatusClosedWin(long id) {
        boolean found = false;
        Opportunity opportunity = lookUpOpportunity(id);
        if (opportunity != null) {
            opportunity.setStatus(Status.CLOSED_WON);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
            colorMessage("The opportunity status has been set to 'closed-won'.", GREEN_TEXT);
            colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
            found = true;
        }
        if(!found) {
            System.out.println("There is no opportunity with this ID. Please try again.");
        }
    }
}
