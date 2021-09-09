package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
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

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    private Account account;
    SalesRep salesRep;
    Contact contact;
    Opportunity opportunity;
    List<Contact> contactList;
    List<Opportunity> opportunityList;

    @BeforeEach
    void setUp() {
        salesRep = new SalesRep("Vivi");
        salesRepRepository.save(salesRep);

        contact = new Contact("John", "123456789", "john@gmail.com", "Big Company");
        contactRepository.save(contact);

        opportunity = new Opportunity(Product.HYBRID, 5, contact, salesRep);
        opportunityRepository.save(opportunity);
        contactList = new ArrayList<>();
        contactList.add(contact);

        opportunityList = new ArrayList<>();
        opportunityList.add(opportunity);

        account = new Account(Industry.ECOMMERCE, 100, "Berlin", "Germany", contactList, opportunityList);
        accountRepository.save(account);

        opportunity.setAccount(account);
        opportunityRepository.save(opportunity);
    }

    @AfterEach
    void tearDown() {
        salesRepRepository.deleteAll();
        contactRepository.deleteAll();
        opportunityRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void findById_validId_account() {
        Optional<Account> accountOptional = accountRepository.findById(account.getId());
        assertTrue(accountOptional.isPresent());
    }

    @Test
    void AccountRepository_CountByCountry_PositiveResult() {
        var oppCount = accountRepository.countOpportunitiesByCountry(account.getCountry());
        assertEquals(account.getOpportunityList().size(), oppCount);
    }

    @Test
    void AccountRepository_CountByCountry_NegativeResult() {
        var oppCount = accountRepository.countOpportunitiesByCountry("Poland");
        assertEquals(0, oppCount);
    }

    @Test
    void AccountRepository_CountByCountryStatus_PositiveResult() {
        var oppCount = accountRepository.countOpportunitiesByCountryStatus("Germany", "OPEN");
        assertEquals(account.getOpportunityList().size(), oppCount);
        Opportunity firstOpportunity = account.getOpportunityList().get(0);
        firstOpportunity.setStatus(Status.CLOSED_LOST);
        opportunityRepository.save(firstOpportunity);
        oppCount = accountRepository.countOpportunitiesByCountryStatus("Germany", "CLOSED_LOST");
        assertEquals(account.getOpportunityList().size(), oppCount);
    }

    @Test
    void AccountRepository_CountByCountryStatus_NegativeResult() {
        var oppCount = accountRepository.countOpportunitiesByCountryStatus("France", "CLOSED_WON");
        assertEquals(0, oppCount);
    }

    @Test
    void AccountRepository_CountByCity_PositiveResult() {
        var oppCount = accountRepository.countOpportunitiesByCity("Berlin");
        assertEquals(account.getOpportunityList().size(), oppCount);
    }

    @Test
    void AccountRepository_CountByCity_NegativeResult() {
        var oppCount = accountRepository.countOpportunitiesByCountry("Madrid");
        assertEquals(0, oppCount);
    }

    @Test
    void AccountRepository_CountByCityStatus_PositiveResult() {
        var oppCount = accountRepository.countOpportunitiesByCityStatus("Berlin", "OPEN");
        assertEquals(account.getOpportunityList().size(), oppCount);
        Opportunity firstOpportunity = account.getOpportunityList().get(0);
        firstOpportunity.setStatus(Status.CLOSED_WON);
        opportunityRepository.save(firstOpportunity);
        oppCount = accountRepository.countOpportunitiesByCityStatus("Berlin", "CLOSED_WON");
        assertEquals(account.getOpportunityList().size(), oppCount);
    }

    @Test
    void AccountRepository_CountByCityStatus_NegativeResult() {
        var oppCount = accountRepository.countOpportunitiesByCityStatus("Berlin", "CLOSED_LOST");
        assertEquals(0, oppCount);
    }

    @Test
    void AccountRepository_CountByIndustry_PositiveResult() {
        var oppCount = accountRepository.countOpportunitiesByIndustry("ECOMMERCE");
        assertEquals(account.getOpportunityList().size(), oppCount);
    }

    @Test
    void AccountRepository_CountByIndustry_NegativeResult() {
        var oppCount = accountRepository.countOpportunitiesByIndustry("OTHER");
        assertEquals(0, oppCount);
    }

    @Test
    void AccountRepository_CountByIndustryStatus_PositiveResult() {
        var oppCount = accountRepository.countOpportunitiesByIndustryStatus("ECOMMERCE", "OPEN");
        assertEquals(account.getOpportunityList().size(), oppCount);
        Opportunity firstOpportunity = account.getOpportunityList().get(0);
        firstOpportunity.setStatus(Status.CLOSED_WON);
        opportunityRepository.save(firstOpportunity);
        oppCount = accountRepository.countOpportunitiesByIndustryStatus("ECOMMERCE", "CLOSED_WON");
        assertEquals(account.getOpportunityList().size(), oppCount);
    }

    @Test
    void AccountRepository_CountByIndustryStatus_NegativeResult() {
        var oppCount = accountRepository.countOpportunitiesByIndustryStatus("ECOMMERCE", "CLOSED_LOST");
        assertEquals(0, oppCount);
    }

}