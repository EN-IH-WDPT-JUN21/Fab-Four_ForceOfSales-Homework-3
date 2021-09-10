package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.apache.commons.lang.WordUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static com.ironhack.FabFour_ForceOfSalesHomework3.enums.Command.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.getCountryList;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;

@Service
public class ReportService {

    private static SalesRepRepository salesRepRepository;
    private static LeadObjectRepository leadObjectRepository;
    private static OpportunityRepository opportunityRepository;
    private static AccountRepository accountRepository;

    @Autowired
    public ReportService(SalesRepRepository salesRepRepository,
                         LeadObjectRepository leadObjectRepository,
                         OpportunityRepository opportunityRepository,
                         AccountRepository accountRepository) {
        this.salesRepRepository = salesRepRepository;
        this.leadObjectRepository = leadObjectRepository;
        this.opportunityRepository = opportunityRepository;
        this.accountRepository = accountRepository;
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

    public static void reportOpportunitiesByProduct() {
        List<String> productList = Arrays.asList("HYBRID", "BOX", "FLATBED");
        String printFormat = printReports();
        for (String product : productList) {
            System.out.format(printFormat, product, opportunityRepository.countOpportunitiesByProduct(product));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesByProductStatus(Status status) {
        List<String> productList = Arrays.asList("HYBRID", "BOX", "FLATBED");
        String printFormat = printReports();
        String convStatus = status.name();
        for (String product : productList) {
            System.out.format(printFormat, product, opportunityRepository.countOpportunitiesByProductStatus(product, convStatus));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesByCountry() {
        List<Account> accountList = accountRepository.findAll();
        List<String> countryList = new ArrayList<>();
        for(Account account: accountList) {
            countryList.add(account.getCountry());
        }
        String printFormat = printReports();
        for(String country: countryList) {
            System.out.format(printFormat, country, accountRepository.countOpportunitiesByCountry(country));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesByCountryStatus(Status status) {
        List<Account> accountList = accountRepository.findAll();
        List<String> countryList = new ArrayList<>();
        String convStatus = status.name();
        for(Account account: accountList) {
            countryList.add(account.getCountry());
        }
        String printFormat = printReports();
        for(String country: countryList) {
            System.out.format(printFormat, country, accountRepository.countOpportunitiesByCountryStatus(country, convStatus));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesByCity() {
        List<Account> accountList = accountRepository.findAll();
        List<String> cityList = new ArrayList<>();
        for(Account account: accountList) {
            cityList.add(account.getCity());
        }
        String printFormat = printReports();
        for(String city: cityList) {
            System.out.format(printFormat, city, accountRepository.countOpportunitiesByCity(city));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesByCityStatus(Status status) {
        List<Account> accountList = accountRepository.findAll();
        List<String> cityList = new ArrayList<>();
        String convStatus = status.name();
        for(Account account: accountList) {
            cityList.add(account.getCity());
        }
        String printFormat = printReports();
        for(String city: cityList) {
            System.out.format(printFormat, city, accountRepository.countOpportunitiesByCityStatus(city, convStatus));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesByIndustry() {
        List<String> industryList = Arrays.asList("ECOMMERCE", "OTHER","MANUFACTURING", "MEDICAL", "PRODUCE");
        String printFormat = printReports();
        for(String industry: industryList) {
            System.out.format(printFormat, industry, accountRepository.countOpportunitiesByIndustry(industry));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static void reportOpportunitiesByIndustryStatus(Status status) {
        List<String> industryList = Arrays.asList("ECOMMERCE", "OTHER","MANUFACTURING", "MEDICAL", "PRODUCE");
        String printFormat = printReports();
        String convStatus = status.name();
        for(String industry: industryList) {
            System.out.format(printFormat, industry, accountRepository.countOpportunitiesByCityStatus(industry, convStatus));
        }
        System.out.format("+------------+---------------------------+%n");
    }

    public static String printReports() {
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        if (opportunityList.size() < 1) {
            colorMessage("+--- There are no Opportunities in the system ---+", TextColor.RED);
        }
        else {
            String printFormat = "| %-10s | %-25d |%n";
            System.out.format("+------------+---------------------------+%n");
            System.out.format("| Product    | Number of Opportunities   |%n");
            System.out.format("+------------+---------------------------+%n");
            return printFormat;
        }
        return "";
    }
}
