package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.LeadObjectService.convertLead;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    SalesRepRepository salesRepRepository;

    @Autowired
    LeadObjectRepository leadObjectRepository;

    Account account;
    Contact contact;
    Contact contact2;
    Contact contact3;
    SalesRep salesRep;
    Opportunity opportunity;
    Opportunity opportunity2;
    Opportunity opportunity3;
    LeadObject lead;
    LeadObject lead2;
    InputStream standardIn;
    List<Contact> contactList = new ArrayList<>();
    List<Opportunity> opportunityList = new ArrayList<>();
    final PrintStream standardOut = System.out;
    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        standardIn = System.in;

        salesRep = new SalesRep("Jane");
        salesRepRepository.save(salesRep);

        lead = new LeadObject("Mick", "987654321", "mick@yahoo.com", "Stones", salesRep);
        leadObjectRepository.save(lead);

        lead2 = new LeadObject("Jane", "111333", "jane@yahoo.com", "Jane's", salesRep);
        leadObjectRepository.save(lead2);

        contact = new Contact("John", "123456789", "john@gmail.com", "Big Company");
        contactRepository.save(contact);

        contact2 = new Contact("Jane", "111333", "jane@gmail.com", "Jane's");
        contactRepository.save(contact2);

        contact3 = new Contact("Janette", "111333", "jane@gmail.com", "Jane's",null);
        contactRepository.save(contact3);

        opportunity = new Opportunity(Product.HYBRID, 40, contact, salesRep);
        opportunityRepository.save(opportunity);

        opportunity2 = new Opportunity(Product.BOX, 3, contact2, salesRep);
        opportunityRepository.save(opportunity2);

        opportunity3 = new Opportunity(Product.HYBRID, 3, contact3, salesRep, null);
        opportunityRepository.save(opportunity3);

        contactList.add(contact);
        opportunityList.add(opportunity);

        account = new Account(Industry.ECOMMERCE, 12, "Paris", "France", contactList, opportunityList);
        accountRepository.save(account);

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        salesRepRepository.deleteAll();
        leadObjectRepository.deleteAll();
        accountRepository.deleteAll();
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: lookupAccount(). Account details printed out as expected.")
    public void Account_lookupAccount_AccountDataPrintedOut(){
        String testString =  "Account: " + account.getId() + ", Industry: " + account.getIndustry() + ", Number of employees: " +
                        account.getEmployeeCount() + ", City: " + account.getCity() + ", Country: " + account.getCountry() +
                      ", Contacts: " + account.printIds("contact") + ", Opportunities:" +
                      account.printIds("opportunity");
        assertEquals(testString, account.toString());
    }

    @Test
    @DisplayName("Test: lookupAccount(). Account not found, error message printed out as expected.")
    public void Account_lookupAccount_AccountErrorMessagePrintedOut(){
        lookUpAccount(55555);
        assertTrue(outputStreamCaptor.toString().trim().contains("There is no Account with id 55555. Please try again."));
    }

    @Test
    @DisplayName("Test: getAccountData(). List of Account details returned as expected.")
    public void Account_getAccountData_AccountDataPReturned(){
        String industry = "other"; String numOfEmployees = "12"; String city = "Paris"; String country = "France";
        String simulatedInput = industry + System.getProperty("line.separator") + numOfEmployees + System.getProperty("line.separator") + city
                + System.getProperty("line.separator") + country + System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        List<Object> dataList = getAccountData();
        System.setIn(savedStandardInputStream);
        assertEquals(Industry.OTHER, dataList.get(0));
    }

    @Test
    @DisplayName("Test: addToAccount(). Account returned as expected.")
    public void Account_addToAccount_AccountReturned(){
        String accountId = String.valueOf(account.getId());
        Account newAccount = addToAccount(accountId, lead, contact, opportunity);
        accountRepository.save(newAccount);
        assertEquals(Product.HYBRID, account.getOpportunityList().get(0).getProduct());
    }

    @Test
    @DisplayName("Test: getAccountId(). Account id returned as expected.")
    public void Account_getAccountId_AccountIdReturned(){
        String accountId = String.valueOf(account.getId());
        String simulatedInput = accountId + System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        List<Object> idList = getAccountId();
        System.setIn(savedStandardInputStream);
        assertEquals(accountId, idList.get(0));
    }

    @Test
    @DisplayName("Test: getAccountId(). Account id not found as expected.")
    public void Account_getAccountId_AccountNotFound(){
        accountRepository.deleteAll();
        String accountId = "1";
        String simulatedInput = accountId + System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        List<Object> idList = getAccountId();
        System.setIn(savedStandardInputStream);
        assertEquals(0, idList.get(0));
    }

    @Test
    @DisplayName("Test: getCountryList(). Return list of countries as expected.")
    public void Account_getCountryList_CountryListReturned() {
        String[] isoCountries = Locale.getISOCountries();
        assertEquals(isoCountries.length, getCountryList().size());
    }

    @Test
    @DisplayName("Test: convertLead(). Lead not converted as it doesn't exist.")
    public void Account_convertLead_LeadNotConvertedNoSuchLead() {
        assertNull(convertLead(1000000));
    }

    @Test
    @DisplayName("Test: createAccount(). Returns correct result.")
    public void Account_createAccount_AccountReturned() {
        long beforeCreateAccount = accountRepository.count();
        List<Contact> newContactList = new ArrayList<>();
        newContactList.add(contact);
        List<Opportunity> newOpportunityList = new ArrayList<>();
        newOpportunityList.add(opportunity);
        List<Object> dataList = new ArrayList<>();
        dataList.add(Industry.OTHER);
        dataList.add(12);
        dataList.add("Berlin");
        dataList.add("Germany");
        dataList.add(newContactList);
        dataList.add(newOpportunityList);
        Account newAccount = createAccount(lead2, contact2, opportunity2, dataList);
        long afterCreateAccount = accountRepository.count();
        assertEquals("Berlin", newAccount.getCity());
        assertEquals(++beforeCreateAccount, afterCreateAccount);
    }

    @Test
    @DisplayName("Test: showAccount(). Prints out correct message as expected.")
    public void Account_showAccounts_MessagePrinted() {
        showAccounts();
        assertTrue(outputStreamCaptor.toString().contains("These are the Accounts logged in our system:"));
    }
}
