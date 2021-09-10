package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.AccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.SalesRepRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.ContactService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ContactServiceTest {

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

    @BeforeEach
    public void setUp() {

        testSalesRep = new SalesRep("Billy");
        salesRepRepository.save(testSalesRep);

        testLead = new LeadObject("Maddy","02020202","maddy@test.com","A company", testSalesRep);
        leadObjectRepository.save(testLead);

        testContactOne = new Contact("Marie","012345678","marie@email.com","A New Company");
        testContactTwo = new Contact("Barry", "079492222", "barry@test.com","Another Company");
        contactRepository.save(testContactOne);
        contactRepository.save(testContactTwo);

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        contactRepository.deleteAll();
        leadObjectRepository.deleteAll();
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Test: createContact(). Successful contact created.")
    public void ContactService_CreateContactTest_CreatedAsExpected() {
        var countBeforeCreation = contactRepository.count();
        createContact(testLead);
        var countAfterCreation = contactRepository.count();
        assert(countAfterCreation == countBeforeCreation + 1);
    }

    @Test
    @DisplayName("Test: createContact(). Contact details match lead details.")
    public void ContactService_CreateContactTest_DetailsAsExpected() {
        Contact createdContact = createContact(testLead);
        assert(testLead.getContactName().equals(createdContact.getContactName()));
        assert(testLead.getCompanyName().equals(createdContact.getCompanyName()));
        assert(testLead.getEmail().equals(createdContact.getEmail()));
        assert(testLead.getPhoneNumber().equals(createdContact.getPhoneNumber()));
    }

//    @Test
//    @DisplayName("Test: lookUpContact(). Contact found.")
//    public void ContactService_LookUpContactTest_ContactFound() {
//        Contact pulledContact = contactRepository.findById(testContactOne.getId()).get();
//        lookUpContact(testContactOne.getId());
//        assertTrue(outputStreamCaptor.toString()
//                .trim().contains(pulledContact.toString()));
//    }

    @Test
    @DisplayName("Test: lookUpContact(). Contact not found.")
    public void ContactService_LookUpContactTest_ContactNotFound() {
        lookUpContact(555);
        assertTrue(outputStreamCaptor.toString()
                .trim().contains("There is no Contact with id 555. Please try again."));
    }

    @Test
    @DisplayName("Test: showAllContacts(). Contacts found.")
    public void ContactService_ShowAllContactsTest_ContactsFound() {
        showAllContacts();
        assertTrue(outputStreamCaptor.toString()
                .trim().contains(
                        "Name: Marie, Phone number: 012345678, Email: marie@email.com"));
        assertTrue(outputStreamCaptor.toString()
                .trim().contains(
                        "Name: Barry, Phone number: 079492222, Email: barry@test.com"));
    }

    @Test
    @DisplayName("Test: showAllContacts(). No contacts found.")
    public void ContactService_ShowAllContactsTest_NoContactsFound() {
        contactRepository.deleteAll();
        showAllContacts();
        assertTrue(outputStreamCaptor.toString()
                .trim().contains("There is no Contacts!"));
    }
}
