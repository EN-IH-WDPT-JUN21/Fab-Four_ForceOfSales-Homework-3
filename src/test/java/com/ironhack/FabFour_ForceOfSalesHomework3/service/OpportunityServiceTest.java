package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
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

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.OpportunityService.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpportunityServiceTest {

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    SalesRepRepository salesRepRepository;

    @Autowired
    LeadObjectRepository leadObjectRepository;

    private Opportunity opportunity;
    private LeadObject lead;
    private Contact contact;

    private InputStream standardIn;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {

        contact = new Contact("John", "123456789", "john@gmail.com", "Big Company");
        contactRepository.save(contact);

        SalesRep salesRep = new SalesRep("Jane");
        salesRepRepository.save(salesRep);

        lead = new LeadObject("John", "123456789", "john@gmail.com", "Big Company", salesRep);
        leadObjectRepository.save(lead);

        opportunity = new Opportunity(Product.HYBRID, 40, contact, salesRep);
        opportunityRepository.save(opportunity);

        standardIn = System.in;
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        salesRepRepository.deleteAll();
        leadObjectRepository.deleteAll();
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: lookUpOpportunity(). Output as expected.")
    void lookUpOpportunity_PositiveTest() {
        lookUpOpportunity(opportunity.getId());
        assertTrue(outputStreamCaptor.toString().trim().contains("Opportunity: " + opportunity.getId() + ", Product: " + opportunity.getProduct() + ", Quantity: " +
                opportunity.getQuantity() + ", Contact: " + opportunity.getDecisionMaker().getContactName() + ", Status: " + opportunity.getStatus()));
    }

    @Test
    @DisplayName("Test: lookUpOpportunity(). Case when the opportunity does not exist.")
    void lookUpOpportunity_NegativeTest() {
        lookUpOpportunity(55555);
        assertTrue(outputStreamCaptor.toString().trim().contains("There is no Opportunity with id 55555. Please try again."));
    }

    @Test
    @DisplayName("Test: updateOpportunityStatusClosedLost(). Status updated as expected, positive test.")
    void updateOpportunityStatusClosedLost_PositiveTest() {
        updateOpportunityStatusClosedLost(opportunity.getId());
        assertEquals(Status.CLOSED_LOST, opportunityRepository.findById(opportunity.getId()).get().getStatus());
    }

    @Test
    @DisplayName("Test: updateOpportunityStatusClosedLost(). Status updated as expected, negative test.")
    void updateOpportunityStatusClosedLost_NegativeTest() {
        updateOpportunityStatusClosedLost(123456);
        assertTrue(outputStreamCaptor.toString().trim().contains("There is no opportunity with this ID. Please try again."));
    }

    @Test
    @DisplayName("Test: updateOpportunityStatusClosedWin(). Status updated as expected, positive test.")
    void updateOpportunityStatusClosedWin_PositiveTest() {
        updateOpportunityStatusClosedWin(opportunity.getId());
        assertEquals(Status.CLOSED_WON, opportunityRepository.findById(opportunity.getId()).get().getStatus());
    }

    @Test
    @DisplayName("Test: updateOpportunityStatusClosedWin(). Status updated as expected, negative test.")
    void updateOpportunityStatusClosedWin_NegativeTest() {
        updateOpportunityStatusClosedWin(123456);
        assertTrue(outputStreamCaptor.toString().trim().contains("There is no opportunity with this ID. Please try again."));
    }

    @Test
    @DisplayName("Test: createOpportunity(). Opportunity created as expected.")
    void createOpportunity_PositiveTest() {
        opportunityRepository.deleteAll();
        String product = "HYBRID"; String quantity = "13";
        String simulatedInput = product + System.getProperty("line.separator") + quantity + System.getProperty("line.separator");
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Opportunity newOpportunity = createOpportunity(lead, contact);
        System.setIn(savedStandardInputStream);
        assertEquals(1, opportunityRepository.count());
        assertEquals("HYBRID", newOpportunity.getProduct().toString());
        }
}
