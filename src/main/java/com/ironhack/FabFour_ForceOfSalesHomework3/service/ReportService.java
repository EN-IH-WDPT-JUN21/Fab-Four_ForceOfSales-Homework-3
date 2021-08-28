package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private static SalesRepRepository salesRepRepository;
    private static LeadObjectRepository leadObjectRepository;

//    public static void reportBySalesRep(String dataType) {
//        List<SalesRep> salesList = salesRepRepository.findAll();
//
//    }

//    public static void reportByLeads(List<SalesRep> salesReps) {
//        List<LeadObject> leadObjectList = leadObjectRepository.findAll();
//        String printFormat = "| %-10d | %-25s |%n";
//        System.out.println("Here are the number of leads per SalesRep:" + "\n");
//        System.out.format("+------------+---------------------------+%n");
//        System.out.format("| Name       | Number of Leads           |%n");
//        System.out.format("+------------+---------------------------+%n");
//        for (SalesRep sales : salesReps) {
//            System.out.format(printFormat, sales.getName(), leadObjectRepository.countBySalesRep(sales.getId()));
//        }
//        System.out.format("+------------+---------------------------+%n");
//    }
}
