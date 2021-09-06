package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeadObjectRepositoryTest {

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
        leadObjectRepository.save(leadObject);
    }

    @AfterEach
    void tearDown() {
        leadObjectRepository.deleteAll();
        salesRepRepository.deleteAll();
    }

    @Test
    void findById_validId_leadObject() {
        Optional<LeadObject> leadObjectOptional = leadObjectRepository.findById(leadObject.getId());
        assertTrue(leadObjectOptional.isPresent());
    }
}