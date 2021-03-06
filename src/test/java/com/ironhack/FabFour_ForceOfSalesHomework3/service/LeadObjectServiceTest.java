package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.validateInput;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.LeadObjectService.*;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Test: validateSalesRepLeadConstructor(). Sales Rep Exists")
    public void LeadObjectService_ValidateSalesRepLeadConstructorTest_SalesRepExists() {
        String salesRepId = Long.toString(testSalesRep.getId());
        String simulatedInput = salesRepId;
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Optional<SalesRep> validatedSalesRep = Optional.ofNullable(validateSalesRepLeadConstructor());
        System.setIn(savedStandardInputStream);

        assertTrue(validatedSalesRep.isPresent());
    }

    @Test
    @DisplayName("Test: lookupLead(). Lead found.")
    public void LeadObjectService_LookupLeadTest_LeadFound(){
        long testLeadId = testLead.getId();
        Optional<LeadObject> foundLead = Optional.of(lookupLead(testLeadId));
        assertTrue(foundLead.isPresent());
    }

    @Test
    @DisplayName("Test: lookupLead(). Lead Not found.")
    public void LeadObjectService_LookupLeadTest_LeadNotFound(){
        lookupLead(55);
        assertTrue(outputStreamCaptor.toString()
                .trim().contains(colorMessage("There is no lead with id 55", TextColor.RED)));
    }

    @Test
    @DisplayName("Test: removeLead(). Lead Removed.")
    public void LeadObjectService_RemoveLeadTest_LeadRemoved(){
        long testLeadId = testLead.getId();
        var leadCount = leadObjectRepository.count();
        removeLead(testLeadId);
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
        long testLeadId = testLead.getId();
        showLeads();
        assertTrue(outputStreamCaptor.toString()
                .trim().contains("Here are the current leads:"));
        assertTrue(outputStreamCaptor.toString()
                .trim().contains("Lead ID    | Contact Name"));
        assertTrue(outputStreamCaptor.toString()
                .trim().contains(Long.toString(testLeadId)));
    }

    @Test
    @DisplayName("Test: showLeads(). No Leads.")
    public void LeadObjectService_ShowLeadsTest_NoLeads() {
        leadObjectRepository.deleteAll();
        showLeads();
        assertTrue(outputStreamCaptor.toString()
                .trim().contains("There are no leads! Try to add some with the 'new lead' command."));
    }
}
