package com.ironhack.FabFour_ForceOfSalesHomework3.dao;

import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Product;
import com.ironhack.FabFour_ForceOfSalesHomework3.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opportunity_generator")
    @SequenceGenerator(name="opportunity_generator", sequenceName = "opportunity_seq")
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

    public Opportunity(Product product, int quantity,
                       Contact decisionMaker, SalesRep sales) {
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setStatus(Status.OPEN);
        setSales(sales);
    }

    public Opportunity(Product product, int quantity, Contact decisionMaker, SalesRep sales, Account account) {
        setProduct(product);
        setQuantity(quantity);
        setDecisionMaker(decisionMaker);
        setStatus(Status.OPEN);
        setSales(sales);
        setAccount(account);
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

