package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactTest {

    private static Contact testContactOne = null;
    private static Contact testContactTwo = null;

    private static Opportunity testOpportunity = null;

    private static Account testAccount = null;

    @BeforeAll
    public static void setUp() {
        testAccount = new Account();
        testOpportunity = new Opportunity();

        testContactOne = new Contact("Marie","012345678","marie@email.com","A New Company");
        testContactTwo = new Contact("Barry", "079492222", "barry@test.com","Another Company", testAccount);
    }

    @Test
    @DisplayName("Test: Contact Constructor. Contact Name Set as expected.")
    public void ContactTest_ContactNameTest_SetAsExpected() {
        assertEquals("Marie",testContactOne.getContactName());
    }

    @Test
    @DisplayName("Test: Contact Constructor. Phone Number Set as expected.")
    public void ContactTest_PhoneNumberTest_SetAsExpected() {
        assertEquals("012345678",testContactOne.getPhoneNumber());
    }

    @Test
    @DisplayName("Test: Contact Constructor. Email Set as expected.")
    public void ContactTest_EmailTest_SetAsExpected() {
        assertEquals("marie@email.com",testContactOne.getEmail());
    }

    @Test
    @DisplayName("Test: Contact Constructor. Company Name Set as expected.")
    public void ContactTest_CompanyNameTest_SetAsExpected() {
        assertEquals("A New Company",testContactOne.getCompanyName());
    }

    @Test
    @DisplayName("Test: Contact Constructor. Account set as expected.")
    public void ContactTest_AccountTest_SetAsExpected() {
        assertEquals(testAccount.getId(),testContactTwo.getAccount().getId());
    }

    @Test
    @DisplayName("Test: SetOpportunity(). Opp can set.")
    public void ContactTest_SetOpportunity_SetAsExpected() {
        testContactOne.setOpportunity(testOpportunity);
        assertEquals(testContactOne.getOpportunity(),testOpportunity);
    }
    
}
