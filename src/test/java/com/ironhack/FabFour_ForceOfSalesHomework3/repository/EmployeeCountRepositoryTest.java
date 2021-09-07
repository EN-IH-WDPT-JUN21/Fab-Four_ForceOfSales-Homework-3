package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeCountRepositoryTest {

    static Account account1;
    static Account account2;
    static Account account3;
    static Account account4;


    @Autowired
    EmployeeCountRepository employeeCountRepository;

    @BeforeEach
    public void setUp() {
        account1 = new Account(Industry.ECOMMERCE, 54, "Paris", "France", null, null);
        account2 = new Account(Industry.ECOMMERCE, 33, "Paris", "France", null, null);
        account3 = new Account(Industry.ECOMMERCE, 12, "Paris", "France", null, null);
        account4 = new Account(Industry.ECOMMERCE, 48, "Paris", "France", null, null);

        employeeCountRepository.saveAll(List.of(account1, account2, account3, account4));
    }


    @AfterEach
    public void tearDown() {
        employeeCountRepository.deleteAll();
    }

    @Test
    public void AccountRepository_getMeanEmployeeCount() {
        Double avg = employeeCountRepository.getMeanEmployeeCount();

        assertEquals(36.75, avg, 0);
    }

    @Test
    public void AccountRepository_getMaxEmployeeCount() {
        Integer max = employeeCountRepository.getMaxEmployeeCount();

        assertEquals(54, max);
    }

    @Test
    public void AccountRepository_getMinEmployeeCount() {
        Integer min = employeeCountRepository.getMinEmployeeCount();

        assertEquals(12, min);
    }

    @Test
    public void AccountRepository_getEmployeeCountList() {
        List<Integer> employeeCounts = employeeCountRepository.getEmployeeCountList();

        assertEquals(4, employeeCounts.size());
    }
}