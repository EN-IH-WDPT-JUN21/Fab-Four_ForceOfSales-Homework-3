package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.OpportunityPerAccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpportunityPerAccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    OpportunityPerAccountService opportunityPerAccountService;

    private Opportunity opportunity1;
    private Opportunity opportunity2;
    private Opportunity opportunity3;
    private Opportunity opportunity4;
    private Opportunity opportunity5;
    private Opportunity opportunity6;
    private Opportunity opportunity7;
    private Opportunity opportunity8;


    private Account account1;
    private Account account2;
    private Account account3;


    @BeforeEach
    public void setUp() {
        opportunity1 = new Opportunity(Product.HYBRID, 30, null, null);
        opportunity2 = new Opportunity(Product.HYBRID, 100, null, null);
        opportunity3 = new Opportunity(Product.HYBRID, 230, null, null);
        opportunity4 = new Opportunity(Product.HYBRID, 121, null, null);
        opportunity5 = new Opportunity(Product.HYBRID, 121, null, null);
        opportunity6 = new Opportunity(Product.HYBRID, 121, null, null);
        opportunity7 = new Opportunity(Product.HYBRID, 121, null, null);
        opportunity8 = new Opportunity(Product.HYBRID, 121, null, null);

        account1 = new Account(Industry.MEDICAL,120,"Warsaw","Poland",null,List.of(opportunity1,opportunity2,opportunity3,opportunity4));
        account2 = new Account(Industry.MEDICAL,120,"Warsaw","Poland",null,List.of(opportunity5,opportunity6,opportunity7));
        account3 = new Account(Industry.MEDICAL,120,"Warsaw","Poland",null,List.of(opportunity8));

        opportunity1.setAccount(account1);
        opportunity2.setAccount(account1);
        opportunity3.setAccount(account1);
        opportunity4.setAccount(account1);
        opportunity5.setAccount(account2);
        opportunity6.setAccount(account2);
        opportunity7.setAccount(account2);
        opportunity8.setAccount(account3);

        accountRepository.saveAll(List.of(account1,account2,account3));
        opportunityRepository.saveAll(List.of(opportunity1, opportunity2, opportunity3, opportunity4,opportunity5,opportunity6,opportunity7,opportunity8));

    }

    @AfterEach
    public void tearDown() {
        opportunityRepository.deleteAll();
    }

    @Test
    void opportunityPerAccountService_getMedian_oddOpportunitiesPerAccount() {
        assertEquals(3, opportunityPerAccountService.getMedianValue());
    }

    @Test
    void opportunityPerAccountService_getMedian_evenOpportunitiesPerAccount() {
        Opportunity opportunity9 = new Opportunity(Product.HYBRID, 101, null, null);
        Opportunity opportunity10 = new Opportunity(Product.HYBRID, 101, null, null);
        Account account4 =  new Account(Industry.ECOMMERCE,120,"Paris","France",null,List.of(opportunity9,opportunity10));

        opportunity9.setAccount(account4);
        opportunity10.setAccount(account4);

        accountRepository.save(account4);
        opportunityRepository.saveAll(List.of(opportunity9,opportunity10));

        assertEquals(2.5, opportunityPerAccountService.getMedianValue(),0.5);

    }

}