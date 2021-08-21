package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Contact;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ContactRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.repository.LeadObjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private static ContactRepository contactRepository;
    private static LeadObjectRepository leadObjectRepository;

    public ContactService(ContactRepository contactRepository,
                          LeadObjectRepository leadObjectRepository) {
        this.contactRepository = contactRepository;
        this.leadObjectRepository = leadObjectRepository;
    }

    public static Contact createContact(LeadObject leadObjectToConvert) {
        //Creates Contact object from Lead
        String contactName = leadObjectToConvert.getContactName();
        String contactPhoneNumber = leadObjectToConvert.getPhoneNumber();
        String contactEmail = leadObjectToConvert.getEmail();
        String contactCompany = leadObjectToConvert.getCompanyName();
        SalesRep contactSalesRep = leadObjectToConvert.getSales();
        Contact contact =  new Contact(contactName, contactPhoneNumber, contactEmail, contactCompany, contactSalesRep);
        contactRepository.save(contact);
        leadObjectToConvert.setContact(contact);
        leadObjectRepository.save(leadObjectToConvert);
        return contact;
    }
}
