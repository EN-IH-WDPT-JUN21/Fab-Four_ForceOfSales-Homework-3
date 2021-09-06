package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product.BOX;
import static com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product.HYBRID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InputOutputServiceTest {

    @Autowired
    static
    LeadObjectRepository leadObjectRepository;

    @Autowired
    static
    AccountRepository accountRepository;

    @Autowired
    static
    SalesRepRepository salesRepRepository;

    static SalesRep salesRep1 = null;
    static LeadObject testLeadOne = null;
    static LeadObject testLeadTwo = null;
    static LeadObject testLeadThree = null;
    static Account testAccountOne = null;
    static Account testAccountTwo = null;


    @BeforeAll
    public static void setup() {
        salesRep1 = new SalesRep("Maddy");
        salesRepRepository.save(salesRep1);

        testLeadOne = new LeadObject("Test One","02030104","test@one.com","A test company",salesRep1);
        testLeadTwo = new LeadObject("Test Two","09080706","test@two.com","A test company",salesRep1);
        testLeadThree = new LeadObject("Test Three","11111111","test@three.com","A test company",salesRep1);
        leadObjectRepository.save(testLeadOne);
        leadObjectRepository.save(testLeadTwo);
        leadObjectRepository.save(testLeadThree);

        testAccountOne = new Account(Industry.OTHER,50,"Berlin","Germany",new ArrayList<>(),new ArrayList<>());
        testAccountTwo = new Account(Industry.MEDICAL,300,"London","UK",new ArrayList<>(),new ArrayList<>());

        testAccountOne.getContactList().add(new Contact("Contact One","02030104","contact@one.com","A test company"));
        testAccountTwo.getContactList().add(new Contact("Contact One","02030104","contact@one.com","A test company"));

        testAccountOne.getOpportunityList().add(new Opportunity(BOX,50,testAccountOne.getContactList().get(0),salesRep1));
        testAccountTwo.getOpportunityList().add(new Opportunity(HYBRID,50,testAccountTwo.getContactList().get(0),salesRep1));

        accountRepository.save(testAccountOne);
        accountRepository.save(testAccountTwo);
    }

    @AfterAll
    public static void tearDown() {
        leadObjectRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Test: exportLeadInformation(). Verify file created.")
    public void InputOutput_ExportLeadInformationTest_FileCreated() {
        String simulatedInput = "LeadTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportLeadInformation();
        System.setIn(savedStandardInputStream);

        File tempFile = new File("LeadTestOne.txt");
        assertTrue(tempFile.exists());
    }

    @Test
    @DisplayName("Test: exportLeadInformation(). Verify file name as expected.")
    public void InputOutput_ExportLeadInformationTest_FileNameAsExpected() {
        String simulatedInput = "LeadTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportLeadInformation();
        System.setIn(savedStandardInputStream);

        File tempFileOne = new File("LeadTestOne.txt");
        assertTrue(tempFileOne.exists());
        File tempFileTwo = new File("LeadTestTwo.txt");
        assertFalse(tempFileTwo.exists());
    }

    @Test
    @DisplayName("Test: exportAccountInformation(). Verify file created.")
    public void InputOutput_ExportAccountInformationTest_FileCreated() {
        String simulatedInput = "AccountTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportAccountInformation();
        System.setIn(savedStandardInputStream);

        File tempFile = new File("AccountTestOne.txt");
        assertTrue(tempFile.exists());
    }

    @Test
    @DisplayName("Test: exportAccountInformation(). Verify file name as expected.")
    public void InputOutput_ExportAccountInformationTest_FileNameAsExpected() {
        String simulatedInput = "AccountTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportAccountInformation();
        System.setIn(savedStandardInputStream);

        File tempFileOne = new File("AccountTestOne.txt");
        assertTrue(tempFileOne.exists());
        File tempFileTwo = new File("AccountTestTwo.txt");
        assertFalse(tempFileTwo.exists());
    }

    @Test
    @DisplayName("Test: exportOpportunityInformation(). Verify file created.")
    public void InputOutput_ExportOppInformationTest_FileCreated() {
        String simulatedInput = "OppTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportOppInformation();
        System.setIn(savedStandardInputStream);

        File tempFile = new File("OppTestOne.txt");
        assertTrue(tempFile.exists());
    }

    @Test
    @DisplayName("Test: exportOpportunityInformation(). Verify file name as expected.")
    public void InputOutput_ExportOppInformationTest_FileNameAsExpected() {
        String simulatedInput = "OppTestOne";
        InputStream savedStandardInputStream = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        InputOutputService.exportOppInformation();
        System.setIn(savedStandardInputStream);

        File tempFileOne = new File("OppTestOne.txt");
        assertTrue(tempFileOne.exists());
        File tempFileTwo = new File("OppTestTwo.txt");
        assertFalse(tempFileTwo.exists());
    }
}
