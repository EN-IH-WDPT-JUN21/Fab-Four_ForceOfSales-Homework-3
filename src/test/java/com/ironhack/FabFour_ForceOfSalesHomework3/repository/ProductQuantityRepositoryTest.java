package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductQuantityRepositoryTest {

    static Opportunity opportunity1;
    static Opportunity opportunity2;
    static Opportunity opportunity3;
    static Opportunity opportunity4;


    @Autowired
    ProductQuantityRepository productQuantityRepository;

    @BeforeEach
    public void setUp() {
        opportunity1 = new Opportunity(Product.HYBRID, 30, null, null);
        opportunity2 = new Opportunity(Product.HYBRID, 100, null, null);
        opportunity3 = new Opportunity(Product.HYBRID, 230, null, null);
        opportunity4 = new Opportunity(Product.HYBRID, 121, null, null);

        productQuantityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4));

    }

    @AfterEach
    public void tearDown() {
        productQuantityRepository.deleteAll();
    }

    @Test
    public void ProductQuantityRepository_getMeanEmployeeCount() {
        Double avg = productQuantityRepository.getMeanProductQuantity();

        assertEquals(120.25, avg, 0);
    }

    @Test
    public void ProductQuantityRepository_getMaxEmployeeCount() {
        Integer max = productQuantityRepository.getMaxProductQuantity();

        assertEquals(230, max);
    }

    @Test
    public void ProductQuantityRepository_getMinEmployeeCount() {
        Integer min = productQuantityRepository.getMinProductQuantity();

        assertEquals(30, min);
    }

    @Test
    public void ProductQuantityRepository_getEmployeeCountList() {
        List<Integer> productQuantities = productQuantityRepository.getProductQuantityList();

        assertEquals(4, productQuantities.size());
    }
}