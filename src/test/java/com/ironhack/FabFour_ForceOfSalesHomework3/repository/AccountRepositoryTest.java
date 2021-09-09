package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
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

    @BeforeEach
    void setUp() {
        SalesRep salesRep = new SalesRep("Vivi");
        salesRepRepository.save(salesRep);

        Contact contact = new Contact("John", "123456789", "john@gmail.com", "Big Company");
        contactRepository.save(contact);

        Opportunity opportunity = new Opportunity(Product.HYBRID, 5, contact, salesRep);
        opportunityRepository.save(opportunity);

        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);

        List<Opportunity> opportunityList = new ArrayList<>();
        opportunityList.add(opportunity);

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

//    @Test
//    void OpportunityRepository_CountByCountry_PositiveResult() {
//        var oppCount = accountRepository.countOpportunitiesByCountry("Germany");
//        assertEquals(1, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByCountry_NegativeResult() {
//        var oppCount = accountRepository.countOpportunitiesByCountry("Poland");
//        assertEquals(0, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByCountryStatus_PositiveResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByCountryStatus("France", "OPEN");
//        assertEquals(2, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByCountryStatus_NegativeResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByCountryStatus("France", "CLOSED_WON");
//        assertEquals(0, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByCity_PositiveResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByCountry("Paris");
//        assertEquals(1, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByCity_NegativeResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByCountry("Madrid");
//        assertEquals(0, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByCityStatus_PositiveResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByCityStatus("Paris", "OPEN");
//        assertEquals(2, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByCityStatus_NegativeResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByCityStatus("Paris", "CLOSED_LOST");
//        assertEquals(0, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByIndustry_PositiveResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByIndustry("ECOMMERCE");
//        assertEquals(1, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByIndustry_NegativeResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByIndustry("OTHER");
//        assertEquals(0, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByIndustryStatus_PositiveResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByIndustryStatus("ECOMMERCE", "OPEN");
//        assertEquals(2, oppCount);
//    }
//
//    @Test
//    void OpportunityRepository_CountByIndustryStatus_NegativeResult() {
//        var oppCount = opportunityRepository.countOpportunitiesByIndustryStatus("ECOMMERCE", "CLOSED_WON");
//        assertEquals(0, oppCount);
//    }

}