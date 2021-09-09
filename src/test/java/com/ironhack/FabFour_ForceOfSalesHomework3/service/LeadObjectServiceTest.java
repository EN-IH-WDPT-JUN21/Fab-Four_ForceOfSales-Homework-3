package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@SpringBootTest
public class LeadObjectServiceTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private LeadObjectRepository leadObjectRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    private SalesRep testSalesRep;
    private LeadObject testLead;
    private Contact testContactOne;
    private Contact testContactTwo;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    static InputStream standardIn;

    @BeforeEach
    public void setUp() {
        // Test outputs
        System.setOut(new PrintStream(outputStreamCaptor));

        // Test inputs
        standardIn = System.in;

        testSalesRep = new SalesRep("Billy");
        salesRepRepository.save(testSalesRep);

        testLead = new LeadObject("Maddy","02020202","maddy@test.com","A company", testSalesRep);
        leadObjectRepository.save(testLead);
    }

    @AfterEach
    public void tearDown() {
        leadObjectRepository.deleteAll();
        System.setOut(standardOut);
    }

//    @Test
//    @DisplayName("Test: createLead(). Created, SalesRep exists.")
//    public void LeadObjectService_CreateLeadTest_CreatedSalesRepExists() {
//        var leadCountBeforeTest = leadObjectRepository.count();
//
//    }
}