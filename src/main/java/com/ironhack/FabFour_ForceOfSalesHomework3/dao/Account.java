package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Industry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account account = (com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account) o;
//        return employeeCount == account.employeeCount && industry == account.industry && city.equals(account.city) && country.equals(account.country) && contactList.equals(account.contactList) && opportunityList.equals(account.opportunityList);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account that = (Account) o;
        return this.getIndustry() == that.getIndustry() && this.getEmployeeCount() == that.getEmployeeCount() && this.getCity() == that.getCity() && this.getCountry() == that.getCountry();
    }

    public String contactIdString(List<Contact> lst) {
        List<Long> idList = new ArrayList<>();
        for(Contact c : lst) {
           idList.add(c.getId());
        }
        return Arrays.toString(idList.toArray()).replace("[", "||").replace("]", "||");
    }

    public String opportunityIdString(List<Opportunity> lst) {
        List<Long> idList = new ArrayList<>();
        for(Opportunity o : lst) {
            idList.add(o.getId());
        }
        return Arrays.toString(idList.toArray()).replace("[", "||").replace("]", "||");
    }

    @Override
    public String toString() {
        return "Account: " + this.getId() + ", Industry: " + this.getIndustry() + ", Number of employees: " +
                this.getEmployeeCount() + ", City: " + this.getCity() + ", Country: " + this.getCountry() +
                ", Contacts: " + contactIdString(this.getContactList()) + ", Opportunities:" + opportunityIdString(this.getOpportunityList());
        }
    }

