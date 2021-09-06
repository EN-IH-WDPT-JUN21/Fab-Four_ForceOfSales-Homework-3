package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.ReportType;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ReportService {

    private static SalesRepRepository salesRepRepository;
    private static LeadObjectRepository leadObjectRepository;
    private static OpportunityRepository opportunityRepository;

    public static void reportBySalesRep() {
        List<SalesRep> salesList = salesRepRepository.findAll();
        String defaultResponse = "Command not recognised. Try again or type: help";
        try {
            System.out.println("Please enter your required report type:");
            Scanner aScanner = new Scanner(System.in);
            System.out.println("LEADS, OPPORTUNITY, CLOSED_WON, CLOSED_LOST, OPEN");
            switch (ReportType.getReportType(aScanner.nextLine().toUpperCase())) {
                case LEAD:
                    reportLeadsBySalesRep(salesList);
                    break;
                case OPPORTUNITY:
                    reportOpportunitiesBySalesRep(salesList);
                    break;
                case CLOSED_WON:
                    reportOpportunitiesBySalesRepAndStatus(salesList,Status.CLOSED_WON);
                    break;
                case CLOSED_LOST:
                    reportOpportunitiesBySalesRepAndStatus(salesList,Status.CLOSED_LOST);
                    break;
                case OPEN:
                    reportOpportunitiesBySalesRepAndStatus(salesList,Status.OPEN);
                    break;
                default:
                    System.out.println(defaultResponse);
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static void reportLeadsBySalesRep(List<SalesRep> salesReps) {
        List<LeadObject> leadObjectList = leadObjectRepository.findAll();
        String printFormat = "| %-10d | %-25s |%n";
        System.out.println("Here are the number of leads per SalesRep:" + "\n");
        System.out.format("+------------+---------------------------+%n");
        System.out.format("| Name       | Number of Leads           |%n");
        System.out.format("+------------+---------------------------+%n");
        for (SalesRep sales : salesReps) {
            System.out.format(printFormat, sales.getName(), leadObjectRepository.countLeadObjectsBySalesRep(sales.getName()));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesBySalesRep(List<SalesRep> salesReps) {
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        String printFormat = "| %-10d | %-25s |%n";
        System.out.println("Here are the number of opportunities per SalesRep:" + "\n");
        System.out.format("+------------+---------------------------+%n");
        System.out.format("| Name       | Number of Opportunities   |%n");
        System.out.format("+------------+---------------------------+%n");
        for (SalesRep sales : salesReps) {
            System.out.format(printFormat, sales.getName(), opportunityRepository.countOpportunitiesBySalesRep(sales.getName()));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesBySalesRepAndStatus(List<SalesRep> salesReps, Status status) {
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        String convStatus = status.toString();
        String printFormat = "| %-10d | %-25s |%n";
        System.out.println(String.format("Here are the number of %b opportunities per SalesRep: \n", convStatus));
        System.out.format("+------------+---------------------------+%n");
        System.out.format("| Name       | Number of Opportunities   |%n");
        System.out.format("+------------+---------------------------+%n");
        for (SalesRep sales : salesReps) {
            System.out.format(printFormat, sales.getName(),opportunityRepository.countOpportunitiesBySalesRepAndStatus(sales.getName(),convStatus));
        }
        System.out.format("+------------+---------------------------+%n");
    }
}
