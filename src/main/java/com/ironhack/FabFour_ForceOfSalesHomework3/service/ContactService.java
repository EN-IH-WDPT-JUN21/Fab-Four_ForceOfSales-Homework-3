package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.TextColor;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private static ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        ContactService.contactRepository = contactRepository;
    }

    public static Contact createContact(LeadObject leadObjectToConvert) {
        //Creates Contact object from Lead
        String contactName = leadObjectToConvert.getContactName();
        String contactPhoneNumber = leadObjectToConvert.getPhoneNumber();
        String contactEmail = leadObjectToConvert.getEmail();
        String contactCompany = leadObjectToConvert.getCompanyName();
        Contact contact =  new Contact(contactName, contactPhoneNumber, contactEmail, contactCompany);
        contactRepository.save(contact);
        return contact;
    }

    public static void lookUpContact(long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);

        if(!optionalContact.isPresent()) {
            colorMessage("There is no Contact with id " + id + ". Please try again.", TextColor.RED);
        }
        else {
            Contact contact = optionalContact.get();
            System.out.println(contact);
        }
    }


    public static void showAllContacts() {
        List<Contact> contacts = contactRepository.findAll();

        if(contacts.size() >0) {
            for(Contact c:contacts) {

                System.out.println(String.format("ID: %d, Name: %s, Phone number: %s, Email: %s, Company: %s.",
                        c.getId(), c.getContactName(), c.getPhoneNumber(), c.getEmail(), c.getCompanyName()));
            }
        } else System.out.println("There is no Contacts!");
    }
}
