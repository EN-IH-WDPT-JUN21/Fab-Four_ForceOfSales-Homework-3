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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_generator")
    @SequenceGenerator(name="contact_generator", sequenceName = "contact_seq", allocationSize=50)
    private long id;
    private String contactName;
    private String phoneNumber;
    private String email;
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToOne(mappedBy = "decisionMaker")
    private Opportunity opportunity;

    public Contact(String contactName,
                   String contactPhoneNumber,
                   String contactEmail,
                   String contactCompany) {
        setContactName(contactName);
        setPhoneNumber(contactPhoneNumber);
        setEmail(contactEmail);
        setCompanyName(contactCompany);
    }

    public Contact(String contactName, String phoneNumber, String email, String companyName, Account account) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.companyName = companyName;
        this.account = account;
    }
}
