package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact("Jane", "555444333", "jane@gmail.com", "Jane Company");
        contactRepository.save(contact);
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
    }

    @Test
    void findById_validId_contact() {
        Optional<Contact> contactOptional = contactRepository.findById(contact.getId());
        assertTrue(contactOptional.isPresent());
    }
}