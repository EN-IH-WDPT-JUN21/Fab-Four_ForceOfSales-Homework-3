package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.EmployeeCountService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.OpportunityPerAccountService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.impl.ProductQuantityService;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.interfaces.IAggregateService;
import org.junit.jupiter.api.Test;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.StatesService.*;
import static org.junit.jupiter.api.Assertions.*;

class StatesServiceTest {

    @Test
    void StateService_recogniseElement_notRecognized() {
        assertThrows(IllegalArgumentException.class, () -> recogniseElement("fsadfasfsfasd"));
    }
}