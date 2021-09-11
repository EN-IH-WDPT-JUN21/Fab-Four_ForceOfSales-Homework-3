package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.ReportService.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceTest {

    @Autowired
    SalesRepRepository salesRepRepository;

    @Autowired
    LeadObjectRepository leadObjectRepository;

    @Autowired
    OpportunityRepository opportunityRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ContactRepository contactRepository;

    Account account;
    Contact contact;
    Contact contact2;
    Contact contact3;
    SalesRep salesRep;
    Opportunity opportunity;
    Opportunity opportunity2;
    Opportunity opportunity3;
    LeadObject lead;
    LeadObject lead2;
    InputStream standardIn;
    List<Contact> contactList = new ArrayList<>();
    List<Opportunity> opportunityList = new ArrayList<>();
    final PrintStream standardOut = System.out;
    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        standardIn = System.in;

        salesRep = new SalesRep("Jane");
        salesRepRepository.save(salesRep);

        lead = new LeadObject("Mick", "987654321", "mick@yahoo.com", "Stones", salesRep);
        leadObjectRepository.save(lead);

        lead2 = new LeadObject("Jane", "111333", "jane@yahoo.com", "Jane's", salesRep);
        leadObjectRepository.save(lead2);

        contact = new Contact("John", "123456789", "john@gmail.com", "Big Company");
        contactRepository.save(contact);

        contact2 = new Contact("Jane", "111333", "jane@gmail.com", "Jane's");
        contactRepository.save(contact2);

        contact3 = new Contact("Janette", "111333", "jane@gmail.com", "Jane's",null);
        contactRepository.save(contact3);

        opportunity = new Opportunity(Product.HYBRID, 40, contact, salesRep);
        opportunityRepository.save(opportunity);

        opportunity2 = new Opportunity(Product.BOX, 3, contact2, salesRep);
        opportunityRepository.save(opportunity2);

        opportunity3 = new Opportunity(Product.HYBRID, 3, contact3, salesRep, null);
        opportunityRepository.save(opportunity3);

        contactList.add(contact);
        opportunityList.add(opportunity);

        account = new Account(Industry.ECOMMERCE, 12, "Paris", "France", contactList, opportunityList);
        accountRepository.save(account);

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
        salesRepRepository.deleteAll();
        leadObjectRepository.deleteAll();
        accountRepository.deleteAll();
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: reportLeadsBySalesRep(). Leads reported as expected.")
    public void Report_reportLeadsBySalesRep() {
        reportLeadsBySalesRep();
        assertTrue(outputStreamCaptor.toString().trim().contains("| Name       | Number of Leads           |"));
    }

    @Test
    @DisplayName("Test: reportOpportunitiesBySalesRep(). Opportunities reported as expected.")
    public void Report_reportOpportunitiesBySalesRep() {
        reportOpportunitiesBySalesRep();
        assertTrue(outputStreamCaptor.toString().trim().contains("| Name    | Number of Opportunities   |"));
    }

    @Test
    @DisplayName("Test: reportOpportunitiesBySalesRepAndStatus(). Opportunities reported as expected.")
    public void reportOpportunitiesBySalesRepAndStatus() {
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByProduct(). Opportunities reported as expected.")
    public void Report_reportOpportunitiesByProduct() {
        reportOpportunitiesByProduct();
        assertTrue(outputStreamCaptor.toString().trim().contains("| Product    | Number of Opportunities   |"));
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByProductStatus(). Opportunities reported as expected.")
    public void reportOpportunitiesByProductStatus() {
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByCountry(). Opportunities reported as expected.")
    public void Report_reportOpportunitiesByCountry() {
        reportOpportunitiesByCountry();
        assertTrue(outputStreamCaptor.toString().trim().contains("| Country    | Number of Opportunities   |"));
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByCountryStatus(). Opportunities reported as expected.")
    public void reportOpportunitiesByCountryStatus() {
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByCity(). Opportunities reported as expected.")
    public void Report_reportOpportunitiesByCity() {
        reportOpportunitiesByCity();
        assertTrue(outputStreamCaptor.toString().trim().contains("| City    | Number of Opportunities   |"));
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByCityStatus(). Opportunities reported as expected.")
    public void reportOpportunitiesByCityStatus() {
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByIndustry(). Opportunities reported as expected.")
    public void Report_reportOpportunitiesByIndustry() {
        reportOpportunitiesByIndustry();
        assertTrue(outputStreamCaptor.toString().trim().contains("| Industry    | Number of Opportunities   |"));
    }

    @Test
    @DisplayName("Test: reportOpportunitiesByIndustryStatus(). Opportunities reported as expected.")
    public void Report_reportOpportunitiesByIndustryStatus_StatusOpen() {
        reportOpportunitiesByIndustryStatus(Status.OPEN);
        assertTrue(outputStreamCaptor.toString().trim().contains("| Industry    | Number of Opportunities   |"));
//        assertTrue(outputStreamCaptor.toString().trim().contains("| ECOMMERCE    | 1                         |"));

    }

    @Test
    @DisplayName("Test: printReports(). Message printed out as expected.")
    public void Report_printReports_NoOpportunities() {
        opportunityRepository.deleteAll();
        printReports("City");
        assertTrue(outputStreamCaptor.toString().trim().contains("+--- There are no Opportunities in the system ---+"));
    }

    @Test
    @DisplayName("Test: printReports(). Message printed out as expected.")
    public void Report_printReports_OpportunitiesExist() {
        printReports("City");
        assertTrue(outputStreamCaptor.toString().trim().contains("+------------+---------------------------+"));
    }

    @Test
    @DisplayName("Test: accountLoop(). Correct values returned as expected.")
    public void  Report_accountLoop_CityListReturned() {
        List<String> cityList = accountLoop("city");
        assertEquals("Paris", cityList.get(0));
    }

    @Test
    @DisplayName("Test: getEnumNames(). Enum names returned as expected.")
    public void Report_getEnumNames_NamesReturned() {
        List<String> enumNames = getEnumNames("product");
        assertTrue(enumNames.contains("HYBRID"));
    }
}