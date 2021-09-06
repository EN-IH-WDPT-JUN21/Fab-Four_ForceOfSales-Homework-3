package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@SpringBootTest
public class LeadObjectRepositoryTest {

    @Autowired
    private LeadObjectRepository leadObjectRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    private LeadObject leadObject;

    @BeforeEach
    void setUp() {
        SalesRep salesRep = new SalesRep("Jane");
        salesRepRepository.save(salesRep);

        leadObject = new LeadObject("John", "123456789", "john@gmail.com", "Small Company", salesRep);
        
        LeadObject leadTest1 = new LeadObject("Buzz", "0000000","buzz@test.com","A Company Name", salesRep1);
        LeadObject leadTest2 = new LeadObject("Woody", "000000000", "woody@test.com", "A company name", salesRep1);
        LeadObject leadTest3 = new LeadObject("Slinky","0000000","slinky@test.com","A company name", salesRep1);
        leadObjectRepository.save(leadObject);  
        leadObjectRepository.save(leadTest1);
        leadObjectRepository.save(leadTest2);
        leadObjectRepository.save(leadTest3);
    }

    @AfterEach
    void tearDown() {
        salesRepRepository.deleteAll();
        leadObjectRepository.deleteAll();
    }

    @Test
    void findById_validId_leadObject() {
        Optional<LeadObject> leadObjectOptional = leadObjectRepository.findById(leadObject.getId());
        assertTrue(leadObjectOptional.isPresent());
    }
    
    @Test
    public void LeadObjectRepository_CountBySalesRepTest_PositiveResult() {
        var leadCount = leadObjectRepository.countLeadObjectsBySalesRep("Maddy");
        assertEquals(3, leadCount);
    }

    @Test
    public void LeadObjectRepository_CountBySalesRepTest_NegativeResult() {
        var leadCount = leadObjectRepository.countLeadObjectsBySalesRep("Big");
        assertEquals(0, leadCount);
    }
}
