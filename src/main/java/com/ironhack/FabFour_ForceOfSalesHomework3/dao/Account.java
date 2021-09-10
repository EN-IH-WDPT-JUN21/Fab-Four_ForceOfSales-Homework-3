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
    @SequenceGenerator(name="account_generator", sequenceName = "account_seq")
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
//        Account account =  o;
//        return employeeCount == account.employeeCount && industry == account.industry && city.equals(account.city) && country.equals(account.country) && contactList.equals(account.contactList) && opportunityList.equals(account.opportunityList);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account that = (Account) o;
        return this.getIndustry().equals(that.getIndustry()) && this.getEmployeeCount() == that.getEmployeeCount() && this.getCity().equals(that.getCity()) && this.getCountry().equals(that.getCountry());
    }

    //Format contactList and opportunityList output: || 1, 4 ||
    public String printIds(String listType) {
        List<Long> idList = new ArrayList<>();
        if(listType.equals("contact")) {
            for (Contact c : this.getContactList()) {
                idList.add(c.getId());
            }
        } else if(listType.equals("opportunity")) {
            for(Opportunity o : this.getOpportunityList()) {
                idList.add(o.getId());
            }
        }
        return Arrays.toString(idList.toArray()).replace("[", "||").replace("]", "||");
    }

    @Override
    public String toString() {
        return "Account: " + this.getId() + ", Industry: " + this.getIndustry() + ", Number of employees: " +
                this.getEmployeeCount() + ", City: " + this.getCity() + ", Country: " + this.getCountry() +
                ", Contacts: " + printIds("contact") + ", Opportunities:" + printIds("opportunity");
        }
    }

