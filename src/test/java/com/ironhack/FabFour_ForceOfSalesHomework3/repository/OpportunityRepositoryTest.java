package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OpportunityRepositoryTest {

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    SalesRepRepository salesRepRepository;

    @Autowired
    ContactRepository contactRepository;

    SalesRep salesRep1 = null;
    Contact contact1 = null;
    private Opportunity opportunity;

    @BeforeEach
    public void setUp() {
        SalesRep salesRep = new SalesRep("Jane");
        salesRep1 = new SalesRep("Vivi");
        salesRepRepository.save(salesRep);
        salesRepRepository.save(salesRep1);
      
        contact1 = new Contact("Buzz", "0000000","buzz@test.com","A Company Name");
        Contact contact = new Contact("John", "123456789", "john@gmail.com", "Big Company");
        contactRepository.save(contact);
        contactRepository.save(contact1);

        Opportunity opportunityTestOne = new Opportunity(Product.HYBRID,5,contact1,salesRep1);
        Opportunity opportunityTestTwo = new Opportunity(Product.FLATBED,5,contact1,salesRep1);
        Opportunity opportunityTestThree = new Opportunity(Product.BOX,5,contact1,salesRep1);
        opportunity = new Opportunity(Product.HYBRID, 40, contact, salesRep);
        opportunityRepository.save(opportunity);  
        opportunityRepository.save(opportunityTestOne);
        opportunityRepository.save(opportunityTestTwo);
        opportunityRepository.save(opportunityTestThree);
    }

    @AfterEach
    void tearDown() {
        salesRepRepository.deleteAll();
        contactRepository.deleteAll();
        opportunityRepository.deleteAll();
    }
  
    void findById_validId_opportunity() {
        Optional<Opportunity> opportunityOptional = opportunityRepository.findById(opportunity.getId());
        assertTrue(opportunityOptional.isPresent());
    }

    @Test
    public void OpportunityRepository_CountBySalesRep_PositiveResult() {
        var oppCount = opportunityRepository.countOpportunitiesBySalesRep("Vivi");
        assertEquals(3,oppCount);
    }

    @Test
    public void OpportunityRepository_CountBySalesRep_NegativeResult() {
        var oppCount = opportunityRepository.countOpportunitiesBySalesRep("Big");
        assertEquals(0,oppCount);
    }

    @Test
    public void OpportunityRepository_CountByStatus_PositiveResult() {
        var oppCount = opportunityRepository.countOpportunitiesBySalesRepAndStatus("Vivi","OPEN");
        assertEquals(3,oppCount);

        Opportunity opportunityTestFour = new Opportunity(Product.BOX,5,contact1,salesRep1);
        opportunityTestFour.setStatus(Status.CLOSED_LOST);
        opportunityRepository.save(opportunityTestFour);
        var secondOppCount = opportunityRepository.countOpportunitiesBySalesRepAndStatus("Vivi","CLOSED_LOST");
        assertEquals(1,secondOppCount);
    }

    @Test
    public void OpportunityRepository_CountByStatus_NegativeResult() {
        var oppCount = opportunityRepository.countOpportunitiesBySalesRepAndStatus("Big","CLOSED_WON");
        assertEquals(0,oppCount);
    }
}
