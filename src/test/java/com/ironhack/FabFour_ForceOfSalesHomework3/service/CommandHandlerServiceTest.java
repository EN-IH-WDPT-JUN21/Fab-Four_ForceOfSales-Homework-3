package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
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

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.CommandHandlerService.handleCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommandHandlerServiceTest {

    @Autowired
    LeadObjectRepository leadObjectRepository = null;

    @Autowired
    SalesRepRepository salesRepRepository = null;

    @Autowired
    ContactRepository contactRepository = null;

    @Autowired
    AccountRepository accountRepository = null;


    LeadObject tempLeadOne = null;
    SalesRep testSalesRep = null;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));

        testSalesRep = new SalesRep("Maddy");
        salesRepRepository.save(testSalesRep);

        tempLeadOne = new LeadObject("Marie","012345678","marie@email.com","A New Company",testSalesRep);
        leadObjectRepository.save(tempLeadOne);
    }

    @AfterEach
    public void tearDown() {
        salesRepRepository.deleteAll();
        leadObjectRepository.deleteAll();
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: handleCommand(). Command without id handled as expected.")
    public void CommandHandler_HandleCommand_CommandWithoutIdRuns() {
        var currentListSize = leadObjectRepository.findAll().size();
        String username= "Jack"; String phoneNumber= "0794922000"; String email = "test@test.com"; String company = "Company";
        String simulatedInput = testSalesRep.getId() + System.getProperty("line.separator") + username + System.getProperty("line.separator") +
                phoneNumber +  System.getProperty("line.separator") + email +
                System.getProperty("line.separator") + company;
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        handleCommand("new lead");
        System.setIn(savedStandardInputStream);
        assertEquals(leadObjectRepository.findAll().size(), ++currentListSize);
    }

//    @Test
//    @DisplayName("Test: createLead(). Validate that newly created lead is added to LeadList.")
//    public void CommandHandler_CreateLeadTest_ValidateLeadAddedToList() {
//        var currentListSize = LeadList.getListOfLeads().size();
//        String username= "Jack"; String phoneNumber= "0794922"; String email = "test@test.com"; String company = "Company";
//        String simulatedInput = username + System.getProperty("line.separator") +
//                phoneNumber +  System.getProperty("line.separator") + email +
//                System.getProperty("line.separator") + company +  System.getProperty("line.separator");
//        InputStream savedStandardInputStream = System.in;
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//        test.createLead();
//        System.setIn(savedStandardInputStream);
//        assertTrue(LeadList.getListOfLeads().size() == currentListSize + 1);
//    }
//
//    @Test
//    @DisplayName("Test: lookupLead(). Return the lead as expected.")
//    public void CommandHandler_LookupLead_LeadReturned() {
//        tempLeadOne = new Lead("Buzz", "0794922", "buzz@lightyear.com", "Toy Story");
//        LeadList.getListOfLeads().add(tempLeadOne);
//        long tempID = tempLeadOne.getId();
//        Lead tempLeadTwo = test.lookupLead(tempID);
//        assertTrue(tempID == tempLeadTwo.getId());
//    }
//
//    @Test
//    @DisplayName("Test: lookupLead(). Do not return the lead, as not in list.")
//    public void CommandHandler_LookupLead_LeadNotReturned() {
//        Lead tempLead = test.lookupLead(99);
//        assertNull(tempLead);
//    }
//
//    @Test
//    @DisplayName("Test: removeLead(). Remove the lead as expected.")
//    public void CommandHandler_RemoveLead_LeadRemoved() {
//        tempLeadOne = new Lead("Buzz", "07949222", "buzz@lightyear.com", "Toy Story");
//        LeadList.getListOfLeads().add(tempLeadOne);
//        int currentListSize = LeadList.getListOfLeads().size();
//        test.removeLead(tempLeadOne.getId());
//        int updatedListSize = LeadList.getListOfLeads().size();
//        assertTrue(currentListSize == updatedListSize + 1);
//    }
//
//    @Test
//    @DisplayName("Test: removeLead(). Do not remove lead, as ID doesn't exist.")
//    public void CommandHandler_RemoveLead_LeadNotRemoved() {
//        int currentListSize = LeadList.getListOfLeads().size();
//        test.removeLead(99);
//        int updatedListSize = LeadList.getListOfLeads().size();
//        assertTrue(currentListSize == updatedListSize);
//    }
//
//    @Test
//    @DisplayName("Test: createContact(). Contact created as expected.")
//    public void CommandHandler_CreateContact_ContactCreated() {
//        tempLeadTwo = new Lead("Mick", "987654321", "mick@yahoo.com", "Stones");
//        Contact newContact = createContact(tempLeadTwo);
//        assertEquals("987654321", newContact.getPhoneNumber());
//    }
//
//    @Test
//    @DisplayName("Test: isInteger(). Return correct boolean value.")
//    public void CommandHandler_isInteger_CorrectValueReturned() {
//        InputStream in = new ByteArrayInputStream("11".getBytes());
//        System.setIn(in);
//        assertTrue(isInteger("11"));
//        in = new ByteArrayInputStream("hello".getBytes());
//        System.setIn(in);
//        assertFalse(isInteger("hello"));
//    }
//
//    @Test
//    @DisplayName("Test: convertLead(). Lead not converted as it doesn't exist.")
//    public void CommandHandler_convertLead_LeadNotConvertedNoSuchLead() {
//        assertNull(convertLead(1000000));
//    }
//
//    @Test
//    @DisplayName("Test: convertLead(). Lead converted as expected.")
//    public void CommandHandler_convertLead_LeadConverted() {
//        String newProduct = "hybrid"; String numOfTrucks = "200"; String industry = "other";
//        String numOfEmployees = "12"; String city = "Paris"; String country = "France";
//        tempLeadTwo = new Lead("Mick", "987654321", "mick@yahoo.com", "Stones");
//        long leadId = tempLeadTwo.getId();
//        LeadList.getListOfLeads().add(tempLeadTwo);
//        String simulatedInput = newProduct + System.getProperty("line.separator") + numOfTrucks + System.getProperty("line.separator")
//                + industry + System.getProperty("line.separator") + numOfEmployees + System.getProperty("line.separator") + city
//                + System.getProperty("line.separator") + country + System.getProperty("line.separator");
//        InputStream savedStandardInputStream = System.in;
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//        Account acc = convertLead(leadId);
//        System.setIn(savedStandardInputStream);
//        assertEquals("Mick", acc.getContactList().get(0).getContactName());
//        assertEquals("Stones", acc.getContactList().get(0).getCompanyName());
//        accountList.remove(acc);
//    }
//
//    @Test
//    @DisplayName("Test: setupAccount(). Return Account object as expected.")
//    public void CommandHandler_setupAccount_AccountReturned() {
//        //newOpportunity, newIndustry, employeeCount, city, country
//        tempLeadTwo = new Lead("Mick", "987654321", "mick@yahoo.com", "Stones");
//        Contact contact = createContact(tempLeadTwo);
//        Opportunity opportunity = new Opportunity(Product.HYBRID, 23, contact);
//        List<Object> accountData = Arrays.asList(opportunity, Industry.ECOMMERCE, 12, "London", "UK");
//        Account account = setupAccount(accountData);
//        assertEquals("London", account.getCity());
//    }
//
//    @Test
//    @DisplayName("Test: getEnumInput(). Return correct enum value as expected.")
//    public void CommandHandler_GetEnumInput_EnumReturned() {
//        InputStream in = new ByteArrayInputStream("box".getBytes());
//        System.setIn(in);
//        assertEquals(Product.BOX, getEnumInput("product"));
//        in = new ByteArrayInputStream("other".getBytes());
//        System.setIn(in);
//        assertEquals(Industry.OTHER, getEnumInput("industry"));
//    }
//
//    @Test
//    @DisplayName("Test: getEnumInput(). Doesn't return correct as invalid input provided.")
//    public void CommandHandler_GetEnumInput_NullReturned() {
//        InputStream in = new ByteArrayInputStream("semi".getBytes());
//        System.setIn(in);
//        assertNull(getEnumInput("product"));
//    }
//
//    @Test
//    @DisplayName("Test: getIntInput(). Return correct int value as expected.")
//    public void CommandHandler_GetIntInput_IntReturned() {
//        InputStream in = new ByteArrayInputStream("200".getBytes());
//        System.setIn(in);
//        assertEquals(200, getIntInput("quantity"));
//    }
//
//    @Test
//    @DisplayName("Test: getIntInput(). Doesn't return correct value as invalid input provided.")
//    public void CommandHandler_GetIntInput_ZeroReturned() {
//        InputStream in = new ByteArrayInputStream("small".getBytes());
//        System.setIn(in);
//        assertEquals(0, getIntInput("quantity"));
//    }
//
//    @Test
//    @DisplayName("Test: getUserInput(). Return correct String value as expected.")
//    public void CommandHandler_GetUserInput_StringReturned() {
//        InputStream in = new ByteArrayInputStream("London".getBytes());
//        System.setIn(in);
//        assertEquals("London", getUserInput("city"));
//    }
//
//    @Test
//    @DisplayName("Test: getUserInput(). Doesn't return correct value as invalid input provided.")
//    public void CommandHandler_GetUserInput_EmptyStringReturned() {
//        InputStream in = new ByteArrayInputStream("London".getBytes());
//        System.setIn(in);
//        assertNull(getUserInput("country"));
//    }
//
//    @Test
//    @DisplayName("Test: errorMessage(). Method runs as expected.")
//    public void CommandHandler_errorMessage_MessagePrinted() {
//        assertEquals("This is a message", colorMessage("This is a message", RED_TEXT));
//    }
//
//    @Test
//    @DisplayName("Test: getCountryList(). Return list of countries as expected.")
//    public void CommandHandler_getCountryList_CountryListReturned() {
//        String[] isoCountries = Locale.getISOCountries();
//        assertEquals(isoCountries.length, getCountryList().size());
//    }
//
//    @Test
//    void CommandHandler_getIdFromInput_PositiveTest() {
//
//        assertEquals(0, CommandHandler.getIdFromInput("test test"));
//        assertEquals(12, CommandHandler.getIdFromInput("test 12"));
//        assertEquals(133, CommandHandler.getIdFromInput("test 133         "));
//        assertEquals(0, CommandHandler.getIdFromInput("         133 /        "));
//        assertEquals(0, CommandHandler.getIdFromInput("test12"));
//    }
//
//    @Test
//    void CommandHandler_getTextWithoutId_PositiveTest() {
//        String text = getTextWithoutId("lookup lead 12");
//        String text2 = getTextWithoutId("lookup lead               12");
//
//        assertEquals(text,"lookup lead");
//        assertEquals(text2,"lookup lead");
//    }
//
//    @Test
//    void CommandHandler_updateOpportunityStatusClosedWin_PositiveTest() {
//        List<Contact> testContactList = new ArrayList<>();
//        Contact testContact = new Contact ("John Smith", "14243434", "johnsmith@gmx.de", "Smith Lab");
//        testContactList.add(testContact);
//
//        List<Opportunity> testOpportunityList = new ArrayList<>();
//        Opportunity testOpportunity = new Opportunity(Product.BOX, 12, testContact);
//        testOpportunityList.add(testOpportunity);
//
//        Account testAccount = new Account(Industry.MANUFACTURING, 345, "New York", "USA", testContactList, testOpportunityList);
//        accountList.add(testAccount);
//
//        Long id = testOpportunity.getId();
//        updateOpportunityStatusClosedWin(id);
//        assertEquals(Status.CLOSED_WON, testOpportunity.getStatus());
//
//        accountList.remove(testAccount);
//    }
//
//    @Test
//    void CommandHandler_updateOpportunityStatusClosedWin_NegativeTest() {
//        updateOpportunityStatusClosedWin(9876);
//        assertEquals("There is no opportunity with this ID. Please try again.", outputStreamCaptor.toString().trim());
//    }
//
//    @Test
//    void updateOpportunityStatusClosedLost_PositiveTest() {
//        List<Contact> testContactList = new ArrayList<>();
//        Contact testContact = new Contact ("John Smith", "14243434", "johnsmith@gmx.de", "Smith Lab");
//        testContactList.add(testContact);
//
//        List<Opportunity> testOpportunityList = new ArrayList<>();
//        Opportunity testOpportunity = new Opportunity(Product.BOX, 12, testContact);
//        testOpportunityList.add(testOpportunity);
//
//        Account testAccount = new Account(Industry.MANUFACTURING, 345, "New York", "USA", testContactList, testOpportunityList);
//        accountList.add(testAccount);
//
//        Long id = testOpportunity.getId();
//        updateOpportunityStatusClosedLost(id);
//        assertEquals(Status.CLOSED_LOST, testOpportunity.getStatus());
//
//        accountList.remove(testAccount);
//    }
//
//    @Test
//    void CommandHandler_updateOpportunityStatusClosedLost_NegativeTest() {
//        updateOpportunityStatusClosedLost(55555);
//        assertEquals("There is no opportunity with this ID. Please try again.", outputStreamCaptor.toString().trim());
//    }
//
//    @Test
//    @DisplayName("Test: voidChecker(). Returns error.")
//    public void CommandHandler_VoidCheckerTest_ErrorExpected() {
//        Lead testLead = null;
//        voidChecker(testLead);
//        assertEquals("Please try again.", outputStreamCaptor.toString()
//                .trim());
//    }
//
//    @Test
//    @DisplayName("Test: voidChecker(). Returns expected String value.")
//    public void CommandHandler_VoidCheckerTest_CorrectValueExpected() {
//        Lead testLead = new Lead("John Smith", "14243434", "johnsmith@gmx.de", "Smith Lab");
//        voidChecker(testLead);
//        assertEquals(testLead.toString(), outputStreamCaptor.toString()
//                .trim());
//    }
}
