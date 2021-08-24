package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Scanner;

import static com.ironhack.FabFour_ForceOfSalesHomework3.service.DataValidatorService.*;
import static com.ironhack.FabFour_ForceOfSalesHomework3.service.InputOutputService.colorMessage;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lead_object")
@Inheritance(strategy = InheritanceType.JOINED)
public class LeadObject {

    public static final String RED_TEXT = "\033[31m";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lead_generator")
    @SequenceGenerator(name="lead_generator", sequenceName = "lead_seq", allocationSize=50)
    protected long id;

    private String contactName;
    private String phoneNumber;
    private String email;
    private String companyName;

    @ManyToOne(fetch = FetchType.EAGER)
    private SalesRep sales;

    public LeadObject(String contactName, String phoneNumber, String email, String companyName, SalesRep sales) {
        setContactName(contactName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setCompanyName(companyName);
        setSales(sales);
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String name) {
        Scanner aScanner = new Scanner(System.in);
        String input = this.contactName = name;;
        while (input.isEmpty() || !containsOnlyLetters(input)) {
            colorMessage("Please provide a valid contact name.", RED_TEXT);
            input = aScanner.nextLine();
        }
        this.contactName = input;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // Verifies that input is not empty, and is also a valid phone number
        Scanner aScanner = new Scanner(System.in);
        String input = phoneNumber;
        while (!validatePhoneNumber(input)) {
            System.out.println("Please provide a valid phone number. It must be between 6 and 15 digits, and can have hyphens or +. Spaces are not allowed.");
            input = aScanner.next();
        }
        this.phoneNumber = input;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Verifies that input is not empty, and is also a valid email
        Scanner aScanner = new Scanner(System.in);
        String input = email;
        while (!validateEmail(input)) {
            System.out.println("Please provide a valid email.");
            input = aScanner.next();
        }
        this.email = input;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        // Verifies that input is not empty
        Scanner aScanner = new Scanner(System.in);
        String input = companyName;
        while (input.isEmpty()) {
            System.out.println("Please provide a valid company name.");
            input = aScanner.nextLine();
        }
        this.companyName = input;
    }

    public SalesRep getSales() {
        return sales;
    }

    public void setSales(SalesRep sales) {
        this.sales = sales;
    }

    // Checks that leads are not duplicated on each attribute except ID. As the ID is auto-incremented, it is assumed
    // that they will not be repeated.
    @Override
    public boolean equals(Object l) {
        if (this == l) return true;
        if (l == null || getClass() != l.getClass()) return false;
        LeadObject that = (LeadObject) l;
        return this.getCompanyName() == that.getCompanyName() &&
                this.getEmail() == that.getEmail() && this.getContactName() == that.getContactName() &&
                this.getPhoneNumber() == that.getPhoneNumber();
    }

    @Override
    public String toString() {
        return "Lead: " + this.getId() + ", Contact: " + this.getContactName() + ", Phone Number: " +
                this.getPhoneNumber() + ", Email: " + this.getEmail() + ", Company Name: " + this.getCompanyName();
    }
}

