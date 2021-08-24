package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name="account_generator", sequenceName = "account_seq", allocationSize=50)
    private long id;

    @Enumerated(EnumType.STRING)
    private Industry industry;

    private int employeeCount;
    private String city;
    private String country;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    private List<Contact> contactList;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    private List<Opportunity> opportunityList;

    public Account(Industry industry,
                   int employeeCount,
                   String city,
                   String country,
                   List<Contact> contactList,
                   List<Opportunity> opportunityList) {
        setIndustry(industry);
        setEmployeeCount(employeeCount);
        setCity(city);
        setCountry(country);
        setContactList(contactList);
        setOpportunityList(opportunityList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account account = (com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account) o;
        return employeeCount == account.employeeCount && industry == account.industry && city.equals(account.city) && country.equals(account.country) && contactList.equals(account.contactList) && opportunityList.equals(account.opportunityList);
    }

    @Override //add case for multiple opportunities/contacts
    public String toString() {
        return "Account: " + this.getId() + ", Industry: " + this.getIndustry() + ", Number of employees: " +
                this.getEmployeeCount() + ", City: " + this.getCity() + ", Country: " + this.getCountry() +
                ", Contact: " + this.getContactList().get(0).getContactName() + ", Company: " + this.getContactList().get(0).getCompanyName() +
                ", Opportunity ID:" + this.getOpportunityList().get(0).getId();
        }
    }

