package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        List<Contact> contactList = new ArrayList<>();
        List<Opportunity> opportunityList = new ArrayList<>();

        account = new Account(Industry.ECOMMERCE, 100, "Berlin", "Germany", contactList, opportunityList);
        accountRepository.save(account);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    void findById_validId_account() {
        Optional<Account> accountOptional = accountRepository.findById(account.getId());
        assertTrue(accountOptional.isPresent());
    }
}