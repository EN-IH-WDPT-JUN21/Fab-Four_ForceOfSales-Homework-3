package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesRepTest {

    private SalesRep salesRep;

    @BeforeEach
    void setUp() {
        salesRep = new SalesRep(1, "Bob", null, null);
    }

    @Test
    @DisplayName("Test: SalesRep Constructor. Validate fields set.")
    public void SalesRep_AllArgsConstructor_Valid() {
        assertEquals(1, salesRep.getId());
        assertEquals("Bob", salesRep.getName());
        assertEquals(null, salesRep.getLeadList());
        assertEquals(null, salesRep.getOpportunityList());

    }

    @Test
    @DisplayName("Test: toString(). Positive Test.")
    public void SalesRep_ToStringTest_ValidateString() {
        String testString = "SalesRep ID: " + salesRep.getId() + ", SalesRep name: " + salesRep.getName();
        assertEquals(testString, salesRep.toString());
    }

    @Test
    @DisplayName("Test: setId(). Positive Test.")
    public void SalesRep_setIdTest_ValidateString() {
        salesRep.setId(200);
        assertEquals(200, salesRep.getId());
    }

    @Test
    @DisplayName("Test: setName(). Positive Test.")
    public void SalesRep_setNameTest_ValidateString() {
        salesRep.setName("Bobby");
        assertEquals("Bobby", salesRep.getName());
    }
}