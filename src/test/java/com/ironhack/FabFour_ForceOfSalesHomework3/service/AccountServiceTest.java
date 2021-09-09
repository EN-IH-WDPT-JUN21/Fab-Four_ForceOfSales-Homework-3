package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
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
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.getUserInput;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.LeadObjectService.convertLead;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Autowired
    private LeadObjectRepository leadObjectRepository;

    private Account account;
    private Contact contact;
    private Contact contact2;
    private Contact contact3;
    private SalesRep salesRep;
    private Opportunity opportunity;
    private Opportunity opportunity2;
    private Opportunity opportunity3;
    private LeadObject lead;
    private LeadObject lead2;
    private InputStream standardIn;
    private List<Contact> contactList = new ArrayList<>();
    private List<Opportunity> opportunityList = new ArrayList<>();
    private final PrintStream standardOut = System.out;
    private static final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

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
                      ", Contacts: " + account.contactIdString(account.getContactList()) + ", Opportunities:" +
                      account.opportunityIdString(account.getOpportunityList());
        assertEquals(testString, account.toString());
    }

    @Test
    @DisplayName("Test: lookupAccount(). Account not found, error message printed out as expected.")
    public void Account_lookupAccount_AccountErrorMessagePrintedOut(){
        lookUpAccount(55555);
        assertTrue(outputStreamCaptor.toString().trim().contains("There is no Account with id 55555. Please try again."));
    }

//    @Test
//    @DisplayName("Test: getAccountData(). List of Account details returned as expected.")
//    public void Account_getAccountData_AccountDataPrintedOut(){
//        String industry = "other"; String numOfEmployees = "12"; String city = "Paris"; String country = "France";
//        String simulatedInput = industry + System.getProperty("line.separator") + numOfEmployees + System.getProperty("line.separator") + city
//                + System.getProperty("line.separator") + country + System.getProperty("line.separator");
//        InputStream savedStandardInputStream = System.in;
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//        List<Object> dataList = getAccountData();
//        System.setIn(savedStandardInputStream);
//        assertEquals("other", dataList.get(0));
//    }

    @Test
    @DisplayName("Test: getAccountId(). Account id returned as expected.")
    public void Account_getAccountId_AccountIdReturned(){}

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

//    @Test
//    @DisplayName("Test: convertLead(). Lead converted as expected.")
//    public void Account_convertLead_LeadConverted() {
//        long leadId = lead.getId();
//        String newProduct = "hybrid"; String numOfTrucks = "200"; String industry = "other";
//        String numOfEmployees = "12"; String city = "Paris"; String country = "France";
//        String simulatedInput = newProduct + System.getProperty("line.separator") + numOfTrucks + System.getProperty("line.separator")
//                + "y" + System.getProperty("line.separator")
//                + industry + System.getProperty("line.separator") + numOfEmployees + System.getProperty("line.separator") + city
//                + System.getProperty("line.separator") + country + System.getProperty("line.separator");
//        InputStream savedStandardInputStream = System.in;
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//        Account acc = convertLead(leadId);
//        System.setIn(savedStandardInputStream);
//        assertEquals("Mick", acc.getContactList().get(0).getContactName());
//        assertEquals("Stones", acc.getContactList().get(0).getCompanyName());
//    }


    @Test
    @DisplayName("Test: getUserInput(). Return correct enum value as expected.")
    public void Account_GetUSerInput_EnumReturned() {
        InputStream in = new ByteArrayInputStream("box".getBytes());
        System.setIn(in);
        assertEquals(Product.BOX, getUserInput("product"));
        in = new ByteArrayInputStream("other".getBytes());
        System.setIn(in);
        assertEquals(Industry.OTHER, getUserInput("industry"));
    }

    @Test
    @DisplayName("Test: getUserInput(). Return correct value as expected.")
    public void Account_GetUSerInput_IntegerReturned() {
        InputStream in = new ByteArrayInputStream("12".getBytes());
        System.setIn(in);
        assertEquals(12, getUserInput("employees"));
    }


    @Test
    @DisplayName("Test: getUserInput(). Doesn't return correct as invalid input provided.")
    public void Account_GetUserInput_NullReturned() {
        InputStream in = new ByteArrayInputStream("semi".getBytes());
        System.setIn(in);
        assertNull(getUserInput("product"));
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
}
