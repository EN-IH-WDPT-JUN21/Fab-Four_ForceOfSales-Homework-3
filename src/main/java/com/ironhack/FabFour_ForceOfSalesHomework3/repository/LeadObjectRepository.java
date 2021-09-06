package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadObjectRepository extends JpaRepository<LeadObject, Long> {

    @Query(
            value = "SELECT sales_rep.name, COUNT(lead_object.id) FROM lead_object INNER JOIN sales_rep ON sales_rep.id = lead_object.sales_id GROUP BY sales_rep.name",
            nativeQuery = true
    )
    long countLeadObjectsBySalesRep();

}
