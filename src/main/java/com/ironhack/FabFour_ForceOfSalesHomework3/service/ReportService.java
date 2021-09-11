package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.getCountryCode;
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

    //Report Leads by the SalesRep
    public static void reportLeadsBySalesRep() {
        List<SalesRep> salesReps = salesRepRepository.findAll();
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

    //Report Opportunities with a given status by the SalesRep
    public static void reportOpportunitiesBySalesRep() {
        List<SalesRep> salesReps = salesRepRepository.findAll();
        String printFormat = printReports("Name    ");
        for (SalesRep sales : salesReps) {
            System.out.format(printFormat, sales.getName().toUpperCase(), opportunityRepository.countOpportunitiesBySalesRep(sales.getName()));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities with a given status by the SalesRep
    public static void reportOpportunitiesBySalesRepAndStatus(Status status) {
        List<SalesRep> salesReps = salesRepRepository.findAll();
        String convStatus = status.name();
        String printFormat = printReports("Name    ");
        for (SalesRep sales : salesReps) {
            System.out.format(printFormat, sales.getName().toUpperCase(), opportunityRepository.countOpportunitiesBySalesRepAndStatus(sales.getName(), convStatus));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities by the Product
    public static void reportOpportunitiesByProduct() {
        List<String> productList = getEnumNames("product");
        String printFormat = printReports("Product ");
        for (String product : productList) {
            System.out.format(printFormat, product, opportunityRepository.countOpportunitiesByProduct(product));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities with a given status by the Product
    public static void reportOpportunitiesByProductStatus(Status status) {
        List<String> productList = getEnumNames("product");
        String printFormat = printReports("Product ");
        String convStatus = status.name();
        for (String product : productList) {
            System.out.format(printFormat, product, opportunityRepository.countOpportunitiesByProductStatus(product, convStatus));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities by the Country
    public static void reportOpportunitiesByCountry() {
        List<String> countryList = accountLoop("country");
        String printFormat = printReports("Country ");
        for(String country: countryList) {
            System.out.format(printFormat, country, accountRepository.countOpportunitiesByCountry(country));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities with a given status by the Country
    public static void reportOpportunitiesByCountryStatus(Status status) {
        List<String> countryList = accountLoop("country");
        String convStatus = status.name();
        String printFormat = printReports("Country ");
        for(String country: countryList) {
            System.out.format(printFormat, getCountryCode(country), accountRepository.countOpportunitiesByCountryStatus(country, convStatus));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities by the City
    public static void reportOpportunitiesByCity() {
        List<String> cityList = accountLoop("city");
        String printFormat = printReports("City    ");
        for(String city: cityList) {
            System.out.format(printFormat, city, accountRepository.countOpportunitiesByCity(city));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities with a given status by the City
    public static void reportOpportunitiesByCityStatus(Status status) {
        List<String> cityList = accountLoop("city");
        String convStatus = status.name();
        String printFormat = printReports("City    ");
        for(String city: cityList) {
            System.out.format(printFormat, city, accountRepository.countOpportunitiesByCityStatus(city, convStatus));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities by the Industry
    public static void reportOpportunitiesByIndustry() {
        List<String> industryList = getEnumNames("industry");
        String printFormat = printReports("Industry");
        for(String industry: industryList) {
            System.out.format(printFormat, industry, accountRepository.countOpportunitiesByIndustry(industry));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Report Opportunities with a given status by the Industry
    public static void reportOpportunitiesByIndustryStatus(Status status) {
        List<String> industryList = getEnumNames("industry");
        String printFormat = printReports("Industry");
        String convStatus = status.name();
        for(String industry: industryList) {
            System.out.format(printFormat, industry, accountRepository.countOpportunitiesByIndustryStatus(industry, convStatus));
        }
        System.out.format("+-----------------------+--------------------------+%n");
    }

    //Format reporting output
    public static String printReports(String value) {
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        if (opportunityList.size() < 1) {
            colorMessage("+--- There are no Opportunities in the system ---+", TextColor.RED);
        }
        else {
            String printFormat = "| %-21s | %-24d |%n";
            System.out.format("+-----------------------+--------------------------+%n");
            System.out.format("| %s              | Number of Opportunities  |%n", value);
            System.out.format("+-----------------------+--------------------------+%n");
            return printFormat;
        }
        return "";
    }

    //Loop through Accounts and add all available Cities or Countries to the elementList
    public static List<String> accountLoop(String listType) {
        List<Account> accountList = accountRepository.findAll();
        List<String> elementList = new ArrayList<>();
        String element = "";
        if (listType.equals("city")) {
            for (Account account : accountList) {
                element = (account.getCity().length() > 20) ? account.getCity().substring(0, 20) : account.getCity();
                elementList.add(element);
            }
        } else if (listType.equals("country")) {
            for (Account account : accountList) {
                element = (account.getCountry().length() > 20) ? account.getCountry().substring(0, 20) : account.getCountry();
                elementList.add(element);
            }
        }
        return elementList;
    }

    //Return the list of enum values toString()
    public static List<String> getEnumNames(String enumType) {
        List<String> enumNames = new ArrayList<>();
        List<Enum> enumValues = new ArrayList<>();
        switch(enumType) {
            case "product":
                enumValues =  Arrays.asList(Product.values());
                break;
            case "industry":
                enumValues = Arrays.asList(Industry.values());
                break;
        }
        enumValues.forEach(enumValue -> enumNames.add(enumValue.toString()));
        return enumNames;
    }
}
