package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    // Value for the contact ID, automatically incremented on each creation
    private static long contactIDCount = 5000;

    @Id
    private long id;
    private String contactName;
    private String phoneNumber;
    private String email;
    private String companyName;

    @ManyToOne(fetch = FetchType.EAGER)
    private SalesRep contactSalesRep;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToOne(mappedBy = "decisionMaker")
    private Opportunity opportunity;

    public Contact(String contactName,
                   String contactPhoneNumber,
                   String contactEmail,
                   String contactCompany,
                   SalesRep contactSalesRep) {
        setId();
        setContactName(contactName);
        setPhoneNumber(contactPhoneNumber);
        setEmail(contactEmail);
        setCompanyName(contactCompany);
        setContactSalesRep(contactSalesRep);
    }

    public static long getContactIDCount() {
        return contactIDCount;
    }

    public void setId() {
        this.id = contactIDCount;
        contactIDCount++;
    }
}
