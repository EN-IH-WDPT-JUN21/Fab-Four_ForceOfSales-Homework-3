package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales_rep")
public class SalesRep {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_generator")
    @SequenceGenerator(name="sales_generator", sequenceName = "sales_seq", allocationSize=50)
    private long id;

    private String name;

    @OneToMany
            (
                    mappedBy = "sales",
                    cascade = CascadeType.MERGE,
                    orphanRemoval = true
            )
    private List<LeadObject> leadList = new ArrayList<>();

    @OneToMany
            (
                    mappedBy = "sales",
                    cascade = CascadeType.MERGE,
                    orphanRemoval = true
            )

    private List<Opportunity> opportunityList = new ArrayList<>();

    public SalesRep(String name) {
        this.name = name;
    }

    public SalesRep(String name, List<LeadObject> leadList, List<Opportunity> opportunityList) {
        this.name = name;
        this.leadList = leadList;
        this.opportunityList = opportunityList;
    }

    @Override
    public String toString() {
        return "SalesRep ID: " + this.getId() + ", SalesRep name: " + this.getName();
    }
}

