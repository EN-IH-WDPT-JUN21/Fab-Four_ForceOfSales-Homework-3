package com.ironhack.FabFour_ForceOfSalesHomework3.service.impl;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ProductQuantityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductQuantityServiceTest {

    @Autowired
    ProductQuantityService productQuantityService;

    @Autowired
    OpportunityRepository opportunityRepository;

    private Opportunity opportunity1;
    private Opportunity opportunity2;
    private Opportunity opportunity3;
    private Opportunity opportunity4;

    @BeforeEach
    public void setUp() {
        opportunity1 = new Opportunity(Product.HYBRID, 30, null, null);
        opportunity2 = new Opportunity(Product.HYBRID, 100, null, null);
        opportunity3 = new Opportunity(Product.HYBRID, 230, null, null);
        opportunity4 = new Opportunity(Product.HYBRID, 121, null, null);

        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4));

    }

    @AfterEach
    public void tearDown() {
        opportunityRepository.deleteAll();
    }

    @Test
    void ProductQuantityService_getMedian_oddProductQuantity() {
        opportunityRepository.save(new Opportunity(Product.HYBRID, 101, null, null));

        assertEquals(101, productQuantityService.getMedianValue());
    }

    @Test
    void ProductQuantityService_getMedian_evenProductQuantity() {
        assertEquals(110.5, productQuantityService.getMedianValue());
    }
}