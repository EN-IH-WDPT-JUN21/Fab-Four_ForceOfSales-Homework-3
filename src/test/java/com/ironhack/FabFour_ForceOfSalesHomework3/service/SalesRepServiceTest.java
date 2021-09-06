package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.SalesRepService.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesRepServiceTest {

    @Autowired
    private SalesRepRepository salesRepRepository;

    private SalesRep salesRep;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        salesRep = new SalesRep("Jane");
        salesRepRepository.save(salesRep);

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        salesRepRepository.deleteAll();

        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: newSalesRep(). Creation successful.")
    void newSalesRep_PositiveTest() {
        List<SalesRep> salesRepList = salesRepRepository.findAll();
        String testName = "Bob" + System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(testName.getBytes()));
        SalesRep result = newSalesRep();
        System.setIn(savedStandardInputStream);
        assertEquals("Bob", result.getName());
    }

    @Test
    @DisplayName("Test: showSalesReps(). Output as expected.")
    void showSalesReps_PositiveTest() {
        showSalesReps();
        assertTrue(outputStreamCaptor.toString().trim().contains(salesRep.getName()));
    }

    @Test
    @DisplayName("Test: showSalesReps(). No existing sales reps.")
    void showSalesReps_NegativeTest() {
        salesRepRepository.deleteAll();
        showSalesReps();
        assertTrue(outputStreamCaptor.toString().trim().contains("There are no SalesReps! Try to add a new one by typing 'new salesRep'."));
    }

    @Test
    @DisplayName("Test: lookUpSalesRep(). Output as expected.")
    void lookUpSalesRep_PositiveTest() {
        lookUpSalesRep(salesRep.getId());
        assertTrue(outputStreamCaptor.toString().trim().contains("SalesRep ID: " + salesRep.getId() + ", SalesRep name: " + salesRep.getName()));
    }

    @Test
    @DisplayName("Test: lookUpSalesRep(). Output as expected.")
    void lookUpSalesRep_NegativeTest() {
        lookUpSalesRep(22222);
        assertTrue(outputStreamCaptor.toString().trim().contains("There is no SalesRep with id 22222. Please try again."));
    }
}