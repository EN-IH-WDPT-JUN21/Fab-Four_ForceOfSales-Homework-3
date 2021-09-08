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

    public static void reports() {
        String defaultResponse = "Command not recognised. Try again or type: help";
        try {
            System.out.println("Please enter your required report type:");
            Scanner aScanner = new Scanner(System.in);
            System.out.println(
                    "Type one of the below statements to execute:\n\n" +
                            "REPORT BY SALESREP\n" +
                            " > Report Lead By SalesRep \n" +
                            " > Report Opportunity By SalesRep \n" +
                            " > Report CLOSED_WON by SalesRep \n" +
                            " > Report CLOSED_LOST by SalesRep \n" +
                            " > Report OPEN by SalesRep \n" +

                            "\nREPORT BY PRODUCT\n" +
                            " > Report Opportunity by the product \n" +
                            " > Report CLOSED_WON by the product \n" +
                            " > Report CLOSED_LOST by the product \n" +
                            " > Report OPEN by the product \n" +

                            "\nREPORT BY COUNTRY\n" +
                            " > Report Opportunity by Country \n" +
                            " > Report CLOSED_WON by Country \n" +
                            " > Report CLOSED_LOST by Country \n" +
                            " > Report OPEN by Country \n" +

                            "\nREPORT BY CITY\n" +
                            " > Report Opportunity by City \n" +
                            " > Report CLOSED_WON by City \n" +
                            " > Report CLOSED_LOST by City \n" +
                            " > Report OPEN by City \n" +

                            "\nREPORT BY INDUSTRY\n" +
                            " > Report Opportunity by Industry \n" +
                            " > Report CLOSED-WON by Industry \n" +
                            " > Report CLOSED-LOST by Industry \n" +
                            " > Report OPEN by Industry \n" +

                            "\nREPORT BY EMPLOYEE COUNT\n" +
                            " > Mean EmployeeCount \n" +
                            " > Median EmployeeCount \n" +
                            " > Max EmployeeCount \n" +
                            " > Min EmployeeCount \n" +

                            "\nREPORT BY QUANTITY\n" +
                            " > Mean Quantity \n" +
                            " > Median Quantity \n" +
                            " > Max Quantity \n" +
                            " > Min Quantity \n" +

                            "\nSHOW ALL DATA PER TYPE\n" +
                            " > Mean Opps per Account \n" +
                            " > Median Opps per Account \n" +
                            " > Max Opps per Account \n" +
                            " > Min Opps per Account \n" +

                            "\nTO QUIT\n" +
                            " > quit - to leave this menu"
            );
            ReportType userInput = ReportType.getReportType(aScanner.nextLine().toUpperCase());
            switch (userInput) {
                case LEADBYSALESREP:
                case OPPBYSALESREP:
                case WONBYSALESREP:
                case LOSTBYSALESREP:
                case OPENBYSALESREP:
                    reportBySalesRep(userInput);
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


    public static void reportBySalesRep(ReportType userInput) {
        List<SalesRep> salesList = salesRepRepository.findAll();
        try {
            switch (userInput) {
                case LEADBYSALESREP:
                    reportLeadsBySalesRep(salesList);
                    break;
                case OPPBYSALESREP:
                    reportOpportunitiesBySalesRep(salesList);
                    break;
                case WONBYSALESREP:
                    reportOpportunitiesBySalesRepAndStatus(salesList,Status.CLOSED_WON);
                    break;
                case LOSTBYSALESREP:
                    reportOpportunitiesBySalesRepAndStatus(salesList,Status.CLOSED_LOST);
                    break;
                case OPENBYSALESREP:
                    reportOpportunitiesBySalesRepAndStatus(salesList,Status.OPEN);
                    break;
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
