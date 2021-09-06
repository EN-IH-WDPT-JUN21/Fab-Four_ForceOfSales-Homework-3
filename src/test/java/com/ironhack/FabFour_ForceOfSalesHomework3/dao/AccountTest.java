package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountTest {

    static Account account;
    static Contact contact;
    static Contact contact1;
    static Opportunity opportunity;
    static SalesRep sales;
    static InputStream standardIn;
    static List<Contact> contactList = new ArrayList<>();
    static List<Opportunity> opportunityList = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        standardIn = System.in;
        contact = new Contact("Mary Jane", "333222111", "maryJ@yahoo.com", "Jane's Emporium");
        contact1 = new Contact("Peter Parker", "999888777", "peterP@yahoo.com", "Webs");
        opportunity = new Opportunity(Product.FLATBED, 25, contact, sales);
        contactList.add(contact);
        opportunityList.add(opportunity);
        account = new Account(Industry.ECOMMERCE, 12, "Paris", "France", contactList, opportunityList);
        sales = new SalesRep("Jeronimo");
    }

    @AfterEach
    public void tearDown() {
        System.setIn(standardIn);
    }

    @Test
    @DisplayName("Test: setIndustry(). Industry set as expected.")
    public void Account_SetIndustry_IndustrySet(){
        account.setIndustry(Industry.PRODUCE);
        assertEquals(Industry.PRODUCE, account.getIndustry());
    }

    @Test
    @DisplayName("Test: setEmployeeCount(). EmployeeCount set as expected.")
    public void Account_SetEmployeeCount_EmployeeCountSet(){
        account.setEmployeeCount(15);
        assertEquals(15, account.getEmployeeCount());
    }

    @Test
    @DisplayName("Test: setCity(). City set as expected.")
    public void Account_SetCity_CitySet(){
        account.setCity("Krakow");
        assertEquals("Krakow", account.getCity());
    }

    @Test
    @DisplayName("Test: setCountry(). Country set as expected.")
    public void Account_SetCountry_CountrySet(){
        account.setCountry("Poland");
        assertEquals("Poland", account.getCountry());
    }

    @Test
    @DisplayName("Test: getContactList(). ContactList is of type List and size of 1 as expected.")
    public void Account_getContactList_ContactListIsAListOfLength1() {
        assertNotNull(account.getContactList());
        assertEquals(1, account.getContactList().size());
    }

    @Test
    @DisplayName("Test: getOpportunityList(). OpportunityList is of type List and size of 1 as expected.")
    public void Account_getOpportunityList_OpportunityListIsAListOfLength1() {
        assertNotNull(account.getOpportunityList());
        assertEquals(1, account.getOpportunityList().size());
    }


    @Test
    @DisplayName("Test: equals(). Two Account objects are equal as expected.")
    public void Account_equals_areEqual(){
        account = new Account(Industry.ECOMMERCE, 12, "Paris", "France", contactList, opportunityList);
        Account equalAccount = new Account(Industry.ECOMMERCE, 12, "Paris", "France", contactList, opportunityList);
        Object nullObject = null;
        assertEquals(account, account);
        assertEquals(equalAccount, equalAccount);
        assertEquals(account, (Account) equalAccount);
        assertNotEquals(account, nullObject);
    }

    @Test
    @DisplayName("Test: toString(). Positive Test.")
    public void Account_ToStringTest_ValidateString() {
        String testString = "Account: " + account.getId() + ", Industry: " + account.getIndustry() + ", Number of employees: " +
                account.getEmployeeCount() + ", City: " + account.getCity() + ", Country: " + account.getCountry()
                +  ", Contacts: " + account.contactIdString(account.getContactList()) + ", Opportunities:" + account.opportunityIdString(account.getOpportunityList());
//                + ", Contact: " + account.getContactList().get(0).getContactName() + ", Company: " + account.getContactList().get(0).getCompanyName()
//                + ", Opportunity ID:" + account.getOpportunityList().get(0).getId();
        assertEquals(testString, account.toString());
    }

    /*
                ", Contacts: " + contactIdString(this.getContactList()) + ", Opportunities:" + opportunityIdString(this.getOpportunityList());
     */
}

