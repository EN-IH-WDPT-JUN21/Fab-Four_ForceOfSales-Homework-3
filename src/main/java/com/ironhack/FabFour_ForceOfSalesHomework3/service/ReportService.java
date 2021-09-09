package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

import static com.ironhack.FabFour_ForceOfSalesHomework3.enums.Command.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;

@Service
public class ReportService {

    private static SalesRepRepository salesRepRepository;
    private static LeadObjectRepository leadObjectRepository;
    private static OpportunityRepository opportunityRepository;

    @Autowired
    public ReportService(SalesRepRepository salesRepRepository, LeadObjectRepository leadObjectRepository, OpportunityRepository opportunityRepository) {
        this.salesRepRepository = salesRepRepository;
        this.leadObjectRepository = leadObjectRepository;
        this.opportunityRepository = opportunityRepository;
    }

    public static void reportLeadsBySalesRep() {
        List<SalesRep> salesReps = salesRepRepository.findAll();
        List<LeadObject> leadObjectList = leadObjectRepository.findAll();
        if (salesReps.size() < 1) {
            colorMessage("+--- There are no SalesReps in the system ---+", TextColor.RED);
        }
        else {
            String printFormat = "| %-10s | %-25d |%n";
            System.out.println("Here are the number of leads per SalesRep:" + "\n");
            System.out.format("+------------+---------------------------+%n");
            System.out.format("| Name       | Number of Leads           |%n");
            System.out.format("+------------+---------------------------+%n");
            for (SalesRep sales : salesReps) {
                System.out.format(printFormat, sales.getName().toUpperCase(), leadObjectRepository.countLeadObjectsBySalesRep(sales.getName()));
            }
            System.out.format("+------------+---------------------------+%n");
        }
    }

    public static void reportOpportunitiesBySalesRep() {
        List<SalesRep> salesReps = salesRepRepository.findAll();
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        if (salesReps.size() < 1) {
            colorMessage("+--- There are no SalesReps in the system ---+", TextColor.RED);
        }
        else {
            String printFormat = "| %-10s | %-25d |%n";
            System.out.println("Here are the number of opportunities per SalesRep:" + "\n");
            System.out.format("+------------+---------------------------+%n");
            System.out.format("| Name       | Number of Opportunities   |%n");
            System.out.format("+------------+---------------------------+%n");
            for (SalesRep sales : salesReps) {
                System.out.format(printFormat, sales.getName().toUpperCase(), opportunityRepository.countOpportunitiesBySalesRep(sales.getName()));
            }
            System.out.format("+------------+---------------------------+%n");
        }
    }

    public static void reportOpportunitiesBySalesRepAndStatus(Status status) {
        List<SalesRep> salesReps = salesRepRepository.findAll();
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        if (salesReps.size() < 1) {
            colorMessage("+--- There are no SalesReps in the system ---+", TextColor.RED);
        }
        else {
            String convStatus = status.name();
            String printFormat = "| %-10s | %-25d |%n";
            System.out.println(String.format("Here are the number of %b opportunities per SalesRep: \n", convStatus));
            System.out.format("+------------+---------------------------+%n");
            System.out.format("| Name       | Number of Opportunities   |%n");
            System.out.format("+------------+---------------------------+%n");
            for (SalesRep sales : salesReps) {
                System.out.format(printFormat, sales.getName().toUpperCase(), opportunityRepository.countOpportunitiesBySalesRepAndStatus(sales.getName(), convStatus));
            }
            System.out.format("+------------+---------------------------+%n");
        }
    }
}
