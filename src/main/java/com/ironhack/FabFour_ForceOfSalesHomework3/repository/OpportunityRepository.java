package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    //Report by SalesRep
    @Query(
            value = "SELECT COUNT(opportunity.id) FROM opportunity INNER JOIN sales_rep ON sales_rep.id = opportunity.sales_id WHERE sales_rep.name = :name",
            nativeQuery = true
    )
    long countOpportunitiesBySalesRep(@Param("name") String name);

    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "INNER JOIN sales_rep ON sales_rep.id = opportunity.sales_id " +
                    "WHERE sales_rep.name = :name AND opportunity.status = :status",
            nativeQuery = true
    )
    long countOpportunitiesBySalesRepAndStatus(@Param("name") String name, @Param("status") String status);

    //Report by Product
    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "WHERE opportunity.product = :product",
            nativeQuery = true
    )
    long countOpportunitiesByProduct(@Param("product") String product);

    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "WHERE opportunity.product = :product AND opportunity.status = :status",
            nativeQuery = true
    )
    long countOpportunitiesByProductStatus(@Param("product") String product, @Param("status") String status);

}