package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.isDuplicateAccount;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.isDuplicateLead;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataValidatorServiceTest {

    @Autowired
    private LeadObjectRepository leadObjectRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Autowired
    private AccountRepository accountRepository;

    private SalesRep salesRep;
    private LeadObject leadObject;
    private Account account;
    private List<Contact> contactList = new ArrayList<>();
    private List<Opportunity> opportunityList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        salesRep = new SalesRep("Jane");
        salesRepRepository.save(salesRep);

        leadObject = new LeadObject("John", "123456789", "john@gmail.com", "Small Company", salesRep);
        leadObjectRepository.save(leadObject);

        account = new Account(Industry.ECOMMERCE, 100, "Berlin", "Germany", contactList, opportunityList);
        accountRepository.save(account);
    }

    @AfterEach
    void tearDown() {
        leadObjectRepository.deleteAll();
        salesRepRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void isDuplicateLead_Valid() {
        LeadObject testLeadObject = new LeadObject("John", "123456789", "john@gmail.com", "Small Company", salesRep);
        assertTrue(isDuplicateLead(testLeadObject));
    }

    @Test
    void isDuplicateLead_Invalid() {
        LeadObject testLeadObject = new LeadObject("Johnny","123456789", "john@gmail.com", "Small Company", salesRep);
        assertFalse(isDuplicateLead(testLeadObject));
    }

    @Test
    void isDuplicateAccount_Valid() {
        Account testAccount = new Account(Industry.ECOMMERCE, 100, "Berlin", "Germany", contactList, opportunityList);
        assertTrue(isDuplicateAccount(testAccount));
    }

    @Test
    void isDuplicateAccount_Invalid() {
        Account testAccount = new Account(Industry.MANUFACTURING, 100, "Berlin", "Germany", contactList, opportunityList);
        assertFalse(isDuplicateAccount(testAccount));
    }
}