package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.EmployeeCountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmployeeCountServiceTest {

    @Autowired
    EmployeeCountService employeeCountService;

    @Autowired
    AccountRepository accountRepository;

    static Account account1;
    static Account account2;
    static Account account3;
    static Account account4;

    @BeforeEach
    public void setUp() {
        account1 = new Account(Industry.ECOMMERCE, 54, "Paris", "France", null, null);
        account2 = new Account(Industry.ECOMMERCE, 33, "Paris", "France", null, null);
        account3 = new Account(Industry.ECOMMERCE, 12, "Paris", "France", null, null);
        account4 = new Account(Industry.ECOMMERCE, 48, "Paris", "France", null, null);

        accountRepository.saveAll(List.of(account1,account2,account3,account4));
    }

    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void EmployeeCountService_getMax() {
        assertEquals(54,employeeCountService.getMaxValue());
    }

    @Test
    void EmployeeCountService_getMean() {
        assertEquals(36.75,employeeCountService.getMeanValue());
    }


    @Test
    void EmployeeCountService_getMin() {
        assertEquals(12,employeeCountService.getMinValue());
    }

    @Test
    void EmployeeCountService_getMedian_oddEmployeeCountList() {
        accountRepository.save(new Account(Industry.ECOMMERCE, 78, "Paris", "France", null, null));

        assertEquals(48.0,employeeCountService.getMedianValue());
    }

    @Test
    void EmployeeCountService_getMedian_evenEmployeeCountList() {
        assertEquals(40.5,employeeCountService.getMedianValue());
    }


}