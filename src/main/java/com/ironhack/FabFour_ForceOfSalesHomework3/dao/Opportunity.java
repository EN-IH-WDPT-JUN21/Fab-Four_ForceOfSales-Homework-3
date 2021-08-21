package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Opportunity {
    private static long opportunityIDCount = 1000;

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private Product product;
    private int quantity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact decisionMaker;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    private SalesRep sales;

    public Opportunity(Product product, int quantity, Contact decisionMaker, SalesRep sales) {
        setId(id);
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setStatus(Status.OPEN);
        setSales(sales);
    }

    public long getId() {
        return Opportunity.opportunityIDCount;
    }

    public void setId(long id) {
        this.id = opportunityIDCount;
        opportunityIDCount++;
    }

    public void setStatus(Status status) {
        Status currentStatus = this.getStatus();
        if(currentStatus == Status.CLOSED_LOST || currentStatus == Status.CLOSED_WON && status == Status.OPEN) {
            this.status = currentStatus;
        } else {
            if(status == Status.OPEN || status == Status.CLOSED_LOST || status == Status.CLOSED_WON) {
                this.status = status;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opportunity that = (Opportunity) o;
        return quantity == that.quantity && product == that.product && decisionMaker.equals(that.decisionMaker) && status == that.status;
    }

    @Override
    public String toString() {
        return "Opportunity: " + this.getId() + ", Product: " + this.getProduct() + ", Quantity: " +
                this.getQuantity() + ", Contact: " + this.getDecisionMaker().getContactName() + ", Status: " + this.getStatus();
    }
}

