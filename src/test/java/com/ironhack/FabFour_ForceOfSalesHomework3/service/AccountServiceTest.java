package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AccountServiceTest {

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
    @DisplayName("Test: lookupAccount(). Account details printed out as expected.")
    public void Account_lookupAccount_AccountDataPrintedOut(){}

    @Test
    @DisplayName("Test: lookupAccount(). Account not found, error message printed out as expected.")
    public void Account_lookupAccount_AccountErrorMessagePrintedOut(){}

    @Test
    @DisplayName("Test: getAccountData(). List of Account details returned as expected.")
    public void Account_getAccountData_AccountDataPrintedOut(){}

    @Test
    @DisplayName("Test: createAccount(). Account created as expected.")
    public void Account_createAccount_AccountCreated(){}

    @Test
    @DisplayName("Test: addToAccount(). Opportunity and Contact added to Account as expected.")
    public void Account_addToAccount_AddedToAccount(){}

    @Test
    @DisplayName("Test: addToAccount(). Opportunity and Contact not added to Account as it doesn't exist.")
    public void Account_addToAccount_NotAddedToAccount(){}

    @Test
    @DisplayName("Test: getAccountId(). Account id returned as expected.")
    public void Account_getAccountId_AccountIdReturned(){}

    @Test
    @DisplayName("Test: getCountryList. Country list returned as expected.")
    public void Account_getCountryList_countryListReturned(){}

}
