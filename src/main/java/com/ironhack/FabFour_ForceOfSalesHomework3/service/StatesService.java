package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.EmployeeCountService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.OpportunityPerAccountService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.ProductQuantityService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.interfaces.IAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;

@Service
public class StatesService {

    private static EmployeeCountService employeeCountService;

    private static ProductQuantityService productQuantityService;

    private static OpportunityPerAccountService opportunityPerAccountService;

    public StatesService(EmployeeCountService employeeCountService, ProductQuantityService productQuantityService, OpportunityPerAccountService opportunityPerAccountService) {
        this.employeeCountService = employeeCountService;
        this.productQuantityService = productQuantityService;
        this.opportunityPerAccountService = opportunityPerAccountService;
    }

    public static void printMeanValues(String command) {
        
        Double meanDouble = recogniseElement(command).getMeanValue();
        
        if(meanDouble!=null) {
            colorMessage("+--- " + command + " = " + meanDouble+" ---+", TextColor.GREEN);
        } else {
            colorMessage("+--- There is no data for " + command +" ---+",TextColor.RED);
        }
    }


    public static void printMedianValues(String command) {

        Double medianDouble = recogniseElement(command).getMedianValue();

        if(medianDouble!=null) {
            colorMessage("+--- "+command + " = " + medianDouble+" ---+",TextColor.GREEN);
        } else {
            colorMessage("+--- There is no data for " + command +" ---+",TextColor.RED);
        }
    }

    public static void printMaxValues(String command) {

        Integer maxInteger = recogniseElement(command).getMaxValue();

        if(maxInteger!=null) {
            colorMessage("+--- "+command + " =  " + maxInteger +" ---+",TextColor.GREEN);
        } else {
            colorMessage("+--- There is no data for " + command +" ---+",TextColor.RED);
        }
    }

    public static void printMinValues(String command) {
        Integer minInteger = recogniseElement(command).getMaxValue();

        if(minInteger!=null) {
            colorMessage("+--- "+command + " =  " + minInteger +" ---+",TextColor.GREEN);
        } else {
            colorMessage("+--- There is no data for " + command +" ---+",TextColor.RED);
        }
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
