package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesRepRepositoryTest {

    @Autowired
    private SalesRepRepository salesRepRepository;

    private SalesRep salesRep;

    @BeforeEach
    void setUp() {
        salesRep = new SalesRep("Vivi");
        salesRepRepository.save(salesRep);
    }

    @AfterEach
    void tearDown() {
        salesRepRepository.deleteAll();
    }

    @Test
    void findById_validId_salesRep() {
        Optional<SalesRep> salesRepOptional = salesRepRepository.findById(salesRep.getId());
        assertTrue(salesRepOptional.isPresent());
    }
}