package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
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
import java.util.Optional;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.LeadObjectService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LeadObjectServiceTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private LeadObjectRepository leadObjectRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    private SalesRep testSalesRep;
    private LeadObject testLead;
    private Contact testContactOne;
    private Contact testContactTwo;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    static InputStream standardIn;

    @BeforeEach
    public void setUp() {
        // Test outputs
        System.setOut(new PrintStream(outputStreamCaptor));

        // Test inputs
        standardIn = System.in;

        testSalesRep = new SalesRep("Billy");
        salesRepRepository.save(testSalesRep);

        testLead = new LeadObject("Maddy","02020202","maddy@test.com","A company", testSalesRep);
        leadObjectRepository.save(testLead);
    }

    @AfterEach
    public void tearDown() {
        leadObjectRepository.deleteAll();
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: createLead(). Created, SalesRep exists.")
    public void LeadObjectService_CreateLeadTest_CreatedSalesRepExists() {
        var leadCountBeforeTest = leadObjectRepository.count();
        String simulatedInput = "1" + System.getProperty("line.separator") +
                "Buzz" +  System.getProperty("line.separator") +
                "02020202" + System.getProperty("line.separator") +
                "buzz@test.com" +  System.getProperty("line.separator") +
                "A company" +  System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        createLead();
        System.setIn(savedStandardInputStream);
        var leadCountAfterTest = leadObjectRepository.count();

        assertEquals(leadCountAfterTest,leadCountBeforeTest + 1);
    }

    @Test
    @DisplayName("Test: validateSalesRepLeadConstructor(). Sales Rep Exists")
    public void LeadObjectService_ValidateSalesRepLeadConstructorTest_SalesRepExists() {
        String simulatedInput = "1";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Optional<SalesRep> validatedSalesRep = Optional.ofNullable(validateSalesRepLeadConstructor());
        System.setIn(savedStandardInputStream);

        assertTrue(validatedSalesRep.isPresent());
    }

//    @Test
//    @DisplayName("Test: validateSalesRepLeadConstructor(). Invalid Input")
//    public void LeadObjectService_ValidateSalesRepLeadConstructorTest_InvalidInput() {
//        salesRepRepository.deleteAll();
//        String simulatedInput = "What is my id?";
//        InputStream savedStandardInputStream = System.in;
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//        validateSalesRepLeadConstructor();
//        System.setIn(savedStandardInputStream);
//
//        assertTrue(outputStreamCaptor.toString()
//                .trim().contains("SalesRep Id can only be a number. Please try again."));
//    }

    @Test
    @DisplayName("Test: lookupLead(). Lead found.")
    public void LeadObjectService_LookupLeadTest_LeadFound(){
        Optional<LeadObject> foundLead = Optional.of(lookupLead(1));
        assertTrue(foundLead.isPresent());
    }

    @Test
    @DisplayName("Test: removeLead(). Lead Removed.")
    public void LeadObjectService_RemoveLeadTest_LeadRemoved(){
        var leadCount = leadObjectRepository.count();
        removeLead(1);
        var leadCountAfterMethod = leadObjectRepository.count();
        assertEquals(leadCountAfterMethod, leadCount - 1);
    }

    @Test
    @DisplayName("Test: countLeads(). Returns expected Lead Count")
    public void LeadObjectService_CountLeadsTest_PositiveResult() {
        var leadCount = leadObjectRepository.count();
        var leadCountFromMethod = countLeads();
        assertEquals(leadCount,leadCountFromMethod);
    }

    @Test
    @DisplayName("Test: showLeads(). Returns leads as expected.")
    public void LeadObjectService_ShowLeadsTest_LeadsAsExpected() {
        showLeads();
        assertTrue(outputStreamCaptor.toString()
                .trim().contains("Lead ID: 1, Contact Name: " + testLead.getContactName() + "."));
    }

    @Test
    @DisplayName("Test: showLeads(). No Leads.")
    public void LeadObjectService_ShowLeadsTest_NoLeads() {
        leadObjectRepository.deleteAll();
        showLeads();
        assertTrue(outputStreamCaptor.toString()
                .trim().contains("There are no leads! Try to add some with the 'new lead' command."));
    }

    //    @Test
//    @DisplayName("Test: convertLead(). Lead converted as expected.")
//    public void Account_convertLead_LeadConverted() {
//        long leadId = lead.getId();
//        String newProduct = "hybrid"; String numOfTrucks = "200"; String industry = "other";
//        String numOfEmployees = "12"; String city = "Paris"; String country = "France";
//        String userInput = "y";
//        String simulatedInput = newProduct + System.getProperty("line.separator") + numOfTrucks + System.getProperty("line.separator")
//                + userInput + System.getProperty("line.separator")
//                + industry + System.getProperty("line.separator") + numOfEmployees + System.getProperty("line.separator") + city
//                + System.getProperty("line.separator") + country + System.getProperty("line.separator");
//        InputStream savedStandardInputStream = System.in;
//        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
//        Account acc = convertLead(leadId);
//        System.setIn(savedStandardInputStream);
//        assertEquals("Mick", acc.getContactList().get(0).getContactName());
//        assertEquals("Stones", acc.getContactList().get(0).getCompanyName());
//    }
}
