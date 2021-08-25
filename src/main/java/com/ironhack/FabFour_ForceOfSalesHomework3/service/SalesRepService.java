package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.GREEN_TEXT;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.RED_TEXT;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.containsOnlyLetters;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.isEmpty;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;

@Service
public class SalesRepService {

//    SalesReps can be added to the CRM by typing the command “New SalesRep” (case insensitive).
//    When a new SalesRep is created the user will be prompted for a name. A name must be supplied to create the new SalesRep.
//    The system should automatically create an id for the SalesRep.

    private static SalesRepRepository salesRepRepository;

    public SalesRepService(SalesRepRepository salesRepRepository) {
        this.salesRepRepository = salesRepRepository;
    }

    public static SalesRep newSalesRep() {
        String salesName = "";
        Scanner aScanner = new Scanner(System.in);

        while(isEmpty(salesName) || !containsOnlyLetters(salesName)) {
            System.out.println("Please provide the name of the SalesRep. The name cannot be empty and can only contain letters and spaces.");
            salesName = aScanner.nextLine();
        }
        SalesRep sales =  new SalesRep(salesName);
        salesRepRepository.save(sales);

        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        colorMessage("SalesRep created. SalesRep ID: " + sales.getId(), GREEN_TEXT);
        colorMessage("++++++++++++++++++++++++++++++++++++++++++++++++++", GREEN_TEXT);
        return sales;
    }

    public static void showSalesReps() {
        List<SalesRep> salesList = salesRepRepository.findAll();
        String printFormat = "| %-10d | %-25s |%n";

        if(salesList.size() > 0) {
            System.out.println("These are the SalesReps logged in our system:" + "\n");
            System.out.format("+------------+---------------------------+%n");
            System.out.format("| ID         | SalesRep name             |%n");
            System.out.format("+------------+---------------------------+%n");
            for (SalesRep sales : salesList) {
                System.out.format(printFormat, sales.getId(), sales.getName());
                //System.out.println("Sales ID: " + sales.getId() + ", Sales Name: " + sales.getName() + ".\n");
            }
            System.out.format("+------------+---------------------------+%n");
        } else System.out.println("There are no SalesReps! Try to add a new one by typing 'new salesRep'.");
    }

    //TODO: Might need to be updated with a more elegant method
    public static void lookUpSalesRep(long id) {
        Optional<SalesRep> salesRepOptional = salesRepRepository.findById(id);
        if(!salesRepOptional.isPresent()) {
            colorMessage("There is no SalesRep with id " + id + ". Please try again.", RED_TEXT);
        } else {
            SalesRep salesRep = salesRepOptional.get();
            System.out.println(salesRep);
        }
    }

    //MADDY DRAFT
    public static SalesRep findSalesRep(long id) {
        Optional<SalesRep> salesRepOptional = salesRepRepository.findById(id);
        if(!salesRepOptional.isPresent()) {
            colorMessage("There is no SalesRep with id " + id + ".", RED_TEXT);
            return null;
        } else {
            SalesRep salesRep = salesRepOptional.get();
            return salesRep;
        }
    }
}
