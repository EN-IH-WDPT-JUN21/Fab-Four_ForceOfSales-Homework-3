package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class LeadObjectTest {
    public static LeadObject testLead = null;
    public static LeadObject testLeadTwo = null;

    public static SalesRep testSalesRep = null;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        testSalesRep = new SalesRep("Maddy");
        testLead = new LeadObject("Marie","012345678","marie@email.com","A New Company",testSalesRep);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: Lead Constructor. Validate Correct Contact Name Set.")
    public void LeadObjectClass_LeadConstructor_CheckCorrectName() {
        assertEquals("Marie", testLead.getContactName());
    }

    @Test
    @DisplayName("Test: Lead Constructor. Validate Correct Phone Number Set.")
    public void LeadObjectClass_LeadConstructor_CheckCorrectPhoneNumber() {
        assertEquals("012345678", testLead.getPhoneNumber());
    }

    @Test
    @DisplayName("Test: Lead Constructor. Validate Correct Email Set.")
    public void LeadObjectClass_LeadConstructor_CheckCorrectEmail() {
        assertEquals("marie@email.com", testLead.getEmail());
        assertEquals("A New Company", testLead.getCompanyName());
    }

    @Test
    @DisplayName("Test: Lead Constructor. Validate Correct Company Name Set.")
    public void LeadObjectClass_LeadConstructor_CheckCorrectCompanyName() {
        assertEquals("A New Company", testLead.getCompanyName());
    }

    @Test
    @DisplayName("Test: setContactName(). Incorrect value on first attempt.")
    public void LeadObjectClass_SetContactNameTest_IncorrectValueProvided() {
        String simulatedInput = "Fin";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        testLeadTwo = new LeadObject("123","12121212","test@two.com","Test Company",testSalesRep);
        System.setIn(savedStandardInputStream);
        assertEquals("Fin",testLeadTwo.getContactName());
    }

    @Test
    @DisplayName("Test: setPhoneNumber(). Message shown when incorrect value provided")
    public void LeadObjectClass_SetPhoneNumberTest_IncorrectValueProvided() {
        String simulatedInput = "123456789";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        testLead.setPhoneNumber("0101");
        System.setIn(savedStandardInputStream);
        assertEquals("123456789",testLead.getPhoneNumber());
    }

    @Test
    @DisplayName("Test: setEmail(). Message shown when incorrect value provided")
    public void LeadObjectClass_SetEmailTest_IncorrectValueProvided() {
        String simulatedInput = "test@buzz.com";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        testLead.setEmail("0101");
        System.setIn(savedStandardInputStream);
        assertEquals("test@buzz.com",testLead.getEmail());
    }

    @Test
    @DisplayName("Test: setCompanyName(). Message shown when incorrect value provided")
    public void LeadObjectClass_SetCompanyNameTest_IncorrectValueProvided() {
        String simulatedInput = "A Company Name";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        testLead.setCompanyName("");
        System.setIn(savedStandardInputStream);
        assertEquals("A Company Name",testLead.getCompanyName());
    }

    @Test
    @DisplayName("Test: getSalesRep(). Asserts that correct SalesRep returned")
    public void LeadObjectClass_GetSalesRep_PositiveCase() {
        assertEquals(testSalesRep, testLead.getSales());
    }

    @Test
    @DisplayName("Test: getSalesRep(). Asserts that incorrect SalesRep returned")
    public void LeadObjectClass_GetSalesRep_NegativeCase() {
        SalesRep testSalesRep2 = new SalesRep("Buzz");
        testLeadTwo = new LeadObject("Tink","03030303","tink@neverland.com","Neverland",testSalesRep2);
        assertNotEquals(testSalesRep, testLeadTwo.getSales());
    }

    @Test
    @DisplayName("Test: toString(). Positive Test.")
    public void LeadObjectClass_ToStringTest_ValidateString() {
        testLeadTwo = new LeadObject("Caddie", "012345678", "caddie@test.com","A Third Company",testSalesRep);
        String testString = "Lead: " + testLeadTwo.getId() + ", Contact: " + testLeadTwo.getContactName() + ", Phone Number: " +
                testLeadTwo.getPhoneNumber() + ", Email: " + testLeadTwo.getEmail() + ", Company Name: " + testLeadTwo.getCompanyName();
        assertEquals(testString, testLeadTwo.toString());
    }

    @Test
    @DisplayName(("Test: equals(). Negative Test."))
    public void LeadObjectClass_EqualsTest_NegativeTest() {
        testLeadTwo = new LeadObject("Rick","012345678","rick@westley.com","Zombies",testSalesRep);
        assertNotEquals(testLeadTwo, testLead);
    }

    @Test
    @DisplayName(("Test: equals(). Positive Test."))
    public void LeadObjectClass_EqualsTest_PositiveTest() {
        long testId = testLead.getId();
        testLeadTwo = new LeadObject("Marie","012345678","marie@email.com","A New Company",testSalesRep);
        testLeadTwo.id = testId;
        assertEquals(testLeadTwo, testLead);
    }

    // Lombok tests

    @Test
    @DisplayName("@AllArgsConstructor")
    public void LeadObjectClass_AllArgsConstructor() {
        testLeadTwo = new LeadObject(10,"Buzz","0000000","buzz@test.com","Company",testSalesRep);
        assertEquals(10,testLeadTwo.getId());
    }

    @Test
    @DisplayName("@NoArgsConstructor")
    public void LeadObjectClass_NoArgsConstructor() {
        testLeadTwo = new LeadObject();
        assertNotNull(testLeadTwo);
    }
}
