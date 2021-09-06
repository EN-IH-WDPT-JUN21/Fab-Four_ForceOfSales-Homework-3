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

    private LeadObject leadTest1;
    private LeadObject leadTest2;
    private LeadObject leadTest3;

    @BeforeEach
    void setUp() {
        SalesRep salesRep = new SalesRep("Maddy");
        salesRepRepository.save(salesRep);

        leadTest1 = new LeadObject("Buzz", "0000000","buzz@test.com","A Company Name", salesRep);
        leadTest2 = new LeadObject("Woody", "000000000", "woody@test.com", "A company name", salesRep);
        leadTest3 = new LeadObject("Slinky","0000000","slinky@test.com","A company name", salesRep);
        leadObjectRepository.save(leadTest1);
        leadObjectRepository.save(leadTest2);
        leadObjectRepository.save(leadTest3);
    }

    @AfterEach
    void tearDown() {
        salesRepRepository.deleteAll();
        leadObjectRepository.deleteAll();
        salesRepRepository.deleteAll();
    }

    @Test
    void findById_validId_leadObject() {
        Optional<LeadObject> leadObjectOptional = leadObjectRepository.findById(leadTest1.getId());
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
