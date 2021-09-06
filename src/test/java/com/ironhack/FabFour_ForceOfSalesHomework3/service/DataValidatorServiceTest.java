package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.*;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataValidatorServiceTest {

    @Autowired
    private LeadObjectRepository leadObjectRepository;

    @Autowired
    private SalesRepRepository salesRepRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private ContactRepository contactRepository;

    private SalesRep salesRep;
    private LeadObject leadObject;
    private Account account;
    private List<Contact> contactList = new ArrayList<>();
    private List<Opportunity> opportunityList = new ArrayList<>();
    private Opportunity opportunity;
    private Contact contact;

    @BeforeEach
    void setUp() {
        salesRep = new SalesRep("Jane");
        salesRepRepository.save(salesRep);

        leadObject = new LeadObject("John", "123456789", "john@gmail.com", "Small Company", salesRep);
        leadObjectRepository.save(leadObject);

        account = new Account(Industry.ECOMMERCE, 100, "Berlin", "Germany", contactList, opportunityList);
        accountRepository.save(account);

        Contact contact = new Contact("John", "123456789", "john@gmail.com", "Big Company");
        contactRepository.save(contact);

        opportunity = new Opportunity(Product.HYBRID, 40, contact, salesRep);
        opportunityRepository.save(opportunity);
    }

    @AfterEach
    void tearDown() {
        leadObjectRepository.deleteAll();
        salesRepRepository.deleteAll();
        accountRepository.deleteAll();
        opportunityRepository.deleteAll();
        contactRepository.deleteAll();
    }

    /*
    TESTS FOR DUPLICATE DATA CHECK
     */
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

    /*
    TESTS FOR E-MAIL ADDRESS
     */
    @Test
    @DisplayName("Test: Correct e-mail address")
    void DataValidator_validateEmail_correctEmailAddress() {
        assertTrue(validateEmail("hello@gmail.com"));
    }

    @Test
    @DisplayName("Test: Correct e-mail address with points, numbers and hyphen")
    void DataValidator_validateEmail_correctEmailAddressWithSymbols() {
        assertTrue(validateEmail("h3.l_l0@gmail.com"));
    }

    @Test
    @DisplayName("Test: Wrong e-mail address with double @")
    void DataValidator_validateEmail_falseEmailAddressDoubleSign() {
        assertFalse(validateEmail("hello@@gmail.com"));
    }

    @Test
    @DisplayName("Test: Wrong e-mail address with double points")
    void DataValidator_validateEmail_falseEmailAddressDoublePoints() {
        assertFalse(validateEmail("hello@gmail..com"));
    }

    @Test
    @DisplayName("Test: Wrong e-mail address with half of e-mail missing")
    void DataValidator_validateEmail_falseEmailAddressHalfMissing() {
        assertFalse(validateEmail("hello@"));
    }

    @Test
    @DisplayName("Test: Wrong e-mail address with end of e-mail missing")
    void DataValidator_validateEmail_falseEmailAddressEndMissing() {
        assertFalse(validateEmail("hello@gmail"));
    }

    @Test
    @DisplayName("Test: Empty e-mail address")
    void DataValidator_validateEmail_falseEmailAddressEmpty() {
        assertFalse(validateEmail(" "));
    }

    @Test
    @DisplayName("Test: E-Mail address with white spaces")
    void DataValidator_validateEmail_falseEmailAddressWhiteSpaces() {
        assertFalse(validateEmail("hel lo@gmail.com"));
    }

    /*
        TESTS FOR TELEPHONE NUMBER
     */
    @Test
    @DisplayName("Test: correct phone number")
    void DataValidator_validatePhoneNumber_correctNumber() {
        assertTrue(validatePhoneNumber("123456789"));
    }

    @Test
    @DisplayName("Test: correct phone number with country code")
    void DataValidator_validatePhoneNumber_correctNumberWithCountryCode() {
        assertTrue(validatePhoneNumber("+49123456789"));
    }

    @Test
    @DisplayName("Test: correct phone number with hyphen")
    void DataValidator_validatePhoneNumber_correctNumberWithHyphens() {
        assertTrue(validatePhoneNumber("123-456-789"));
    }

    @Test
    @DisplayName("Test: Empty phone number")
    void DataValidator_validatePhoneNumber_wrongNumberEmpty() {
        assertFalse(validatePhoneNumber(""));
    }

    @Test
    @DisplayName("Test: phone number too short")
    void DataValidator_validatePhoneNumber_wrongNumberTooShort() {
        assertFalse(validatePhoneNumber("123"));
    }

    @Test
    @DisplayName("Test: phone number too long")
    void DataValidator_validatePhoneNumber_wrongNumberTooLong() {
        assertFalse(validatePhoneNumber("01234567891234567890"));
    }

    @Test
    @DisplayName("Test: wrong phone number with spaces")
    void DataValidator_validatePhoneNumber_correctNumberWithSpaces() {
        assertFalse(validatePhoneNumber("    0 7 949 22222  2  "));
    }

    @Test
    @DisplayName("Test: phone number contains letters")
    void DataValidator_validatePhoneNumber_wrongNumberWithLetters() {
        assertFalse(validatePhoneNumber("123a45678"));
    }

    @Test
    @DisplayName("Test: phone number contains other symbol")
    void DataValidator_validatePhoneNumber_wrongNumberWithSymbols() {
        assertFalse(validatePhoneNumber("123!=45678"));
    }


    /*
    TESTS FOR EMPTY CHECK
     */
    @Test
    @DisplayName("Test: input doesn't have any signs")
    void DataValidator_isEmpty_noInput() {
        assertTrue(isEmpty(""));
    }

    @Test
    @DisplayName("Test: input only contains white spaces")
    void DataValidator_isEmpty_withWhiteSpaces() {
        assertTrue(isEmpty("          "));
    }

    @Test
    @DisplayName("Test: input only contains white spaces and a sign")
    void DataValidator_isEmpty_containsCharacter() {
        assertFalse(isEmpty("    .      "));
    }

    /*
    TESTS TO CHECK EXISTENCE OF DATA
     */
    @Test
    void leadExists_PositiveTest() {
        assertTrue(leadExists(String.valueOf(leadObject.getId())));
    }

    @Test
    void leadExists_NegativeTest() {
        assertFalse(leadExists("123456"));
    }

    @Test
    void opportunityExists_PositiveTest() {
        assertTrue(opportunityExists(String.valueOf(opportunity.getId())));
    }

    @Test
    void opportunityExists_NegativeTest() {
        assertFalse(opportunityExists("123456"));
    }

    @Test
    void accountExists_PositiveTest() {
        assertTrue(accountExists(String.valueOf(account.getId())));
    }

    @Test
    void accountExists_NegativeTest() {
        assertFalse(accountExists("987654"));
    }

    @Test
    void salesRepExists_PositiveTest() {
        assertTrue(salesRepExists(String.valueOf(salesRep.getId())));
    }

    @Test
    void salesRepExists_NegativeTest() {
        assertFalse(salesRepExists("555555"));
    }

    /*
    TESTS TO CHECK THE FORM OF A STRING
     */
    @Test
    @DisplayName("Test: Input String contains only letters")
    void DataValidator_containsOnlyLetters_PositiveTest() {
        assertTrue(containsOnlyLetters("Berlin"));
    }

    @Test
    @DisplayName("Test: Input String contains letters and white space")
    void DataValidator_containsOnlyLetters_PositiveTestWithWhiteSpaces() {
        assertTrue(containsOnlyLetters("new york"));
    }

    @Test
    @DisplayName("Test: Input String contains numbers")
    void DataValidator_containsOnlyLetters_NegativeTestWithNumbers() {
        assertFalse(containsOnlyLetters("P4r1s"));
    }

    @Test
    @DisplayName("Test: Input String contains other signs")
    void DataValidator_containsOnlyLetters_NegativeTestWithOtherSigns() {
        assertFalse(containsOnlyLetters("Lon!don"));
    }

    /*
    TESTS TO CHECK THE COUNTRY NAME
     */
    @Test
    @DisplayName("Test: validateCountryName(). Return correct boolean value as expected.")
    public void DataValidator_validateCountryName_CorrectBooleanReturned() {
        assertTrue(validateCountryName("Andorra"));
        assertFalse(validateCountryName("And"));
    }
}