package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

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
        String salesName;
        Scanner aScanner = new Scanner(System.in);
        System.out.println("Please provide the Sales Rep name");
        salesName = aScanner.nextLine();
        SalesRep sales =  new SalesRep(salesName);
        salesRepRepository.save(sales);
        return sales;
    }

    public static void showSales() {
        List<SalesRep>  salesList = salesRepRepository.findAll();
        if(salesList.size() > 0) {
            for (SalesRep sales : salesList) {
                System.out.println("Sales ID: " + sales.getId() + ", Sales Name: " + sales.getName() + ".\n");
            }
        } else System.out.println("There are no Sales Reps! Try to add some with the 'new sales' command");
    }
}
