package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LeadObjectRepositoryTest {

    @Autowired
    LeadObjectRepository leadObjectRepository;

    @Autowired
    SalesRepRepository salesRepRepository;

    SalesRep salesRep1 = null;

    @BeforeEach
    public void setUp() {
        salesRep1 = new SalesRep("Maddy");
        salesRepRepository.save(salesRep1);
        LeadObject leadTest1 = new LeadObject("Buzz", "0000000","buzz@test.com","A Company Name", salesRep1);
        LeadObject leadTest2 = new LeadObject("Woody", "000000000", "woody@test.com", "A company name", salesRep1);
        LeadObject leadTest3 = new LeadObject("Slinky","0000000","slinky@test.com","A company name", salesRep1);
        leadObjectRepository.save(leadTest1);
        leadObjectRepository.save(leadTest2);
        leadObjectRepository.save(leadTest3);
    }

    @AfterEach
    public void tearDown() {
        salesRepRepository.deleteAll();
        leadObjectRepository.deleteAll();
    }

    @Test
    public void countBySalesRep_ResultsAsExpected() {
        var leadCount = leadObjectRepository.countLeadObjectsBySalesRep();
        assertEquals(3, leadCount);
    }
}
