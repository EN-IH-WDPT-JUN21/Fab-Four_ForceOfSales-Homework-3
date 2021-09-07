package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.EmployeeCountService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.OpportunityPerAccountService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.ProductQuantityService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.interfaces.IAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;

@Service
public class StatesService {

    public static final String RED_TEXT = "\033[31m";
    public static final String GREEN_TEXT = "\u001B[32m";

    private static EmployeeCountService employeeCountService;

    private static ProductQuantityService productQuantityService;

    private static OpportunityPerAccountService opportunityPerAccountService;

    public StatesService(EmployeeCountService employeeCountService, ProductQuantityService productQuantityService, OpportunityPerAccountService opportunityPerAccountService) {
        this.employeeCountService = employeeCountService;
        this.productQuantityService = productQuantityService;
        this.opportunityPerAccountService = opportunityPerAccountService;
    }

    public static void printMeanValues(String command) {
        colorMessage("+--- " + command + " =  " + recogniseElement(command).getMeanValue()+" ---+","\u001B[32m");
    }


    public static void printMedianValues(String command) {
        colorMessage("+--- "+command + " =  " + recogniseElement(command).getMedianValue()+" ---+","\u001B[32m");
    }

    public static void printMaxValues(String command) {
        colorMessage("+--- "+command + " =  " + recogniseElement(command).getMaxValue()+" ---+","\u001B[32m");

    }

    public static void printMinValues(String command) {
        colorMessage("+--- "+command + " =  " + recogniseElement(command).getMinValue()+" ---+","\u001B[32m");

    }

    public static IAggregateService recogniseElement(String command) {
        if (command.contains("EMPLOYEECOUNT"))
            return employeeCountService;

        if (command.contains("OPPS PER ACCOUNT"))
            return opportunityPerAccountService;

        if (command.contains("QUANTITY"))
            return productQuantityService;

        throw new IllegalArgumentException("Not recognized command: " + command);
    }
}
