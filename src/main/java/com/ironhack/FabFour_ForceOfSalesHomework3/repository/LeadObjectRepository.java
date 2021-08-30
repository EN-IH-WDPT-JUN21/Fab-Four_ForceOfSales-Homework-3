package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.LeadObject;
import com.ironhack.FabFour_ForceOfSalesHomework3.dao.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadObjectRepository extends JpaRepository<LeadObject, Long> {

//    @Query("SELECT COUNT(ID) FROM LEAD_OBJECT WHERE SALES_ID = :salesRepId")
//    long countBySalesRep(@Param("salesRepId") long salesRepId);

}
