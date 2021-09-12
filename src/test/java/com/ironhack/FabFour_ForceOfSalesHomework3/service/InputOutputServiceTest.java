package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product.BOX;
import static com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product.HYBRID;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.AccountService.showCountryList;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InputOutputServiceTest {

    @Autowired
    LeadObjectRepository leadObjectRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SalesRepRepository salesRepRepository;

    SalesRep salesRep1 = null;
    LeadObject testLeadOne = null;
    LeadObject testLeadTwo = null;
    LeadObject testLeadThree = null;
    Account testAccountOne = null;
    Account testAccountTwo = null;
    final PrintStream standardOut = System.out;
    InputStream standardIn;
    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setup() {
        salesRep1 = new SalesRep("Maddy");
        salesRepRepository.save(salesRep1);

        testLeadOne = new LeadObject("Test One", "02030104", "test@one.com", "A test company", salesRep1);
        testLeadTwo = new LeadObject("Test Two", "09080706", "test@two.com", "A test company", salesRep1);
        testLeadThree = new LeadObject("Test Three", "11111111", "test@three.com", "A test company", salesRep1);
        leadObjectRepository.save(testLeadOne);
        leadObjectRepository.save(testLeadTwo);
        leadObjectRepository.save(testLeadThree);

        testAccountOne = new Account(Industry.OTHER, 50, "Berlin", "Germany", new ArrayList<>(), new ArrayList<>());
        testAccountTwo = new Account(Industry.MEDICAL, 300, "London", "UK", new ArrayList<>(), new ArrayList<>());

        testAccountOne.getContactList().add(new Contact("Contact One", "02030104", "contact@one.com", "A test company"));
        testAccountTwo.getContactList().add(new Contact("Contact One", "02030104", "contact@one.com", "A test company"));

        testAccountOne.getOpportunityList().add(new Opportunity(BOX, 50, testAccountOne.getContactList().get(0), salesRep1));
        testAccountTwo.getOpportunityList().add(new Opportunity(HYBRID, 50, testAccountTwo.getContactList().get(0), salesRep1));

        accountRepository.save(testAccountOne);
        accountRepository.save(testAccountTwo);

        standardIn = System.in;
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        leadObjectRepository.deleteAll();
        accountRepository.deleteAll();
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    @Test
    @DisplayName("Test: exportLeadInformation(). Verify file created.")
    public void InputOutput_ExportLeadInformationTest_FileCreated() {
        String simulatedInput = "LeadTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportLeadInformation();
        System.setIn(savedStandardInputStream);

        File tempFile = new File("LeadTestOne.txt");
        assertTrue(tempFile.exists());
    }

    @Test
    @DisplayName("Test: exportLeadInformation(). Verify file name as expected.")
    public void InputOutput_ExportLeadInformationTest_FileNameAsExpected() {
        String simulatedInput = "LeadTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportLeadInformation();
        System.setIn(savedStandardInputStream);

        File tempFileOne = new File("LeadTestOne.txt");
        assertTrue(tempFileOne.exists());
        File tempFileTwo = new File("LeadTestTwo.txt");
        assertFalse(tempFileTwo.exists());
    }

    @Test
    @DisplayName("Test: exportAccountInformation(). Verify file created.")
    public void InputOutput_ExportAccountInformationTest_FileCreated() {
        String simulatedInput = "AccountTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportAccountInformation();
        System.setIn(savedStandardInputStream);

        File tempFile = new File("AccountTestOne.txt");
        assertTrue(tempFile.exists());
    }

    @Test
    @DisplayName("Test: exportAccountInformation(). Verify file name as expected.")
    public void InputOutput_ExportAccountInformationTest_FileNameAsExpected() {
        String simulatedInput = "AccountTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportAccountInformation();
        System.setIn(savedStandardInputStream);

        File tempFileOne = new File("AccountTestOne.txt");
        assertTrue(tempFileOne.exists());
        File tempFileTwo = new File("AccountTestTwo.txt");
        assertFalse(tempFileTwo.exists());
    }

    @Test
    @DisplayName("Test: exportOpportunityInformation(). Verify file created.")
    public void InputOutput_ExportOppInformationTest_FileCreated() {
        String simulatedInput = "OppTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportOppInformation();
        System.setIn(savedStandardInputStream);

        File tempFile = new File("OppTestOne.txt");
        assertTrue(tempFile.exists());
    }

    @Test
    @DisplayName("Test: exportOpportunityInformation(). Verify file name as expected.")
    public void InputOutput_ExportOppInformationTest_FileNameAsExpected() {
        String simulatedInput = "OppTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportOppInformation();
        System.setIn(savedStandardInputStream);

        File tempFileOne = new File("OppTestOne.txt");
        assertTrue(tempFileOne.exists());
        File tempFileTwo = new File("OppTestTwo.txt");
        assertFalse(tempFileTwo.exists());
    }

    @Test
    @DisplayName("Test: isInteger(). Return correct boolean value.")
    public void InputOutput_isInteger_CorrectValueReturned() {
        InputStream in = new ByteArrayInputStream("11".getBytes());
        System.setIn(in);
        assertTrue(isInteger("11"));
        in = new ByteArrayInputStream("hello".getBytes());
        System.setIn(in);
        assertFalse(isInteger("hello"));
    }

    @Test
    @DisplayName("Test: isLong(). Return correct boolean value.")
    public void InputOutput_isLong_CorrectValueReturned() {
        InputStream in = new ByteArrayInputStream("11".getBytes());
        System.setIn(in);
        assertTrue(isLong("11"));
        in = new ByteArrayInputStream("hello".getBytes());
        System.setIn(in);
        assertFalse(isLong("hello"));
    }

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
    @DisplayName("Test: errorMessage(). Method runs as expected.")
    public void InputOutput_errorMessage_MessagePrinted() {
        assertEquals("This is a message", colorMessage("This is a message", TextColor.RED));
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_NoAccount() {
        Object testObject = validateInput("555555", "accountId");
        assertEquals("no account", testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_BadIdFormat() {
        Object testObject = validateInput("accountId", "accountId");
        assertEquals("bad input", testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_GoBack() {
        Object testObject = validateInput("go back", "accountId");
        assertEquals("go back", testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_Industry() {
        Object testObject = validateInput("other", "industry");
        assertEquals(Industry.OTHER, testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_AccountChoice_CreateAccount() {
        String industry = "other"; String numOfEmployees = "12"; String city = "Paris"; String country = "France";
        String simulatedInput = industry + System.getProperty("line.separator") + numOfEmployees + System.getProperty("line.separator") + city
                + System.getProperty("line.separator") + country + System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Object testObject  = validateInput("y", "account");
        System.setIn(savedStandardInputStream);
        assertEquals(Industry.OTHER, ((List) testObject).get(0));
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_AccountChoice_AddToAccount() {
        String accountId = String.valueOf(testAccountOne.getId());
        String simulatedInput = accountId + System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        List<Object> testObjectList  = (List<Object>) validateInput("n", "account");
        System.setIn(savedStandardInputStream);
        assertEquals(accountId, testObjectList.get(0));
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_Quantity() {
        Object testObject = validateInput("12", "quantity");
        assertEquals(12, testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_Employees() {
        Object testObject = validateInput("12", "employees");
        assertEquals(12, testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_City() {
        Object testObject = validateInput("London", "city");
        assertEquals("London", testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_Country() {
        Object testObject = validateInput("Poland", "country");
        assertEquals("Poland", testObject);
    }

    @Test
    @DisplayName("Test: validateInput(). Method runs as expected.")
    public void InputOutput_validateInput_ShowCountries() {
        InputStream in = new ByteArrayInputStream("Poland".getBytes());
        System.setIn(in);
        assertEquals("Poland", validateInput("show countries", "country"));
    }
}
