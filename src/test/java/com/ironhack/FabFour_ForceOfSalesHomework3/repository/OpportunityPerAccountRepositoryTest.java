package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OpportunityPerAccountRepositoryTest {

    static Opportunity opportunity1;
    static Opportunity opportunity2;
    static Opportunity opportunity3;
    static Opportunity opportunity4;
    static Opportunity opportunity5;
    static Opportunity opportunity6;
    static Opportunity opportunity7;
    static Opportunity opportunity8;

    static Account account1;
    static Account account2;
    static Account account3;

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    OpportunityPerAccountRepository opportunityPerAccountRepository;

    @Autowired
    AccountRepository accountRepository;

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
    public void OpportunityPerAccountRepository_getMeanEmployeeCount() {
        Double avg = opportunityPerAccountRepository.getMeanOpportunityCountPerAccount();
        assertEquals(2.66, avg, 0.01);
    }

    @Test
    public void OpportunityPerAccountRepository_getMaxEmployeeCount() {
        Integer max = opportunityPerAccountRepository.getMaxOpportunityCountPerAccount();

        assertEquals(4, max);
    }

    @Test
    public void OpportunityPerAccountRepository_getMinEmployeeCount() {
        Integer min = opportunityPerAccountRepository.getMinOpportunityCountPerAccount();
        assertEquals(1, min);
    }

    @Test
    public void OpportunityPerAccountRepository_getEmployeeCountList() {
        List<Integer> opportunityCountPerAccountList = opportunityPerAccountRepository.getListOpportunityCountPerAccount();

        assertEquals(3, opportunityCountPerAccountList.size());
    }
}