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
    private static long salesIDCount = 6000;
    @Id
    private long id;

    private String name;

    @OneToMany
            (
                    mappedBy = "sales",
                    cascade = CascadeType.ALL,
                    orphanRemoval = true
            )
    private List<LeadObject> leadList = new ArrayList<>();

    @OneToMany
            (
                    mappedBy = "sales",
                    cascade = CascadeType.ALL,
                    orphanRemoval = true
            )

    private List<Opportunity> opportunityList = new ArrayList<>();

    public SalesRep(String name) {
        this.name = name;
    }

    public long getId() {
        return salesIDCount;
    }

    public void setId() {
        this.id = salesIDCount;
        salesIDCount++;
    }


}

