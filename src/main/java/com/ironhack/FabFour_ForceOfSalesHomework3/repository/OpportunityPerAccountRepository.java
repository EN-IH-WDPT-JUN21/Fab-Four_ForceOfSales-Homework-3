package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityPerAccountRepository extends JpaRepository<Opportunity, Long> {

    @Query(value="SELECT AVG(cast(opps as DOUBLE)) FROM (SELECT ACCOUNT_ID, COUNT(*) AS opps FROM OPPORTUNITY GROUP BY ACCOUNT_ID)", nativeQuery = true)
    Double getMeanOpportunityCountPerAccount();

    @Query(value="SELECT MAX(cast(opps as DOUBLE)) FROM (SELECT ACCOUNT_ID, COUNT(*) AS opps FROM OPPORTUNITY GROUP BY ACCOUNT_ID)", nativeQuery = true)
    Integer getMaxOpportunityCountPerAccount();

    @Query(value="SELECT MIN(cast(opps as DOUBLE)) FROM (SELECT ACCOUNT_ID, COUNT(*) AS opps FROM OPPORTUNITY GROUP BY ACCOUNT_ID)", nativeQuery = true)
    Integer getMinOpportunityCountPerAccount();

    @Query(value="SELECT opps FROM (SELECT ACCOUNT_ID, COUNT(*) AS opps FROM OPPORTUNITY GROUP BY ACCOUNT_ID)", nativeQuery = true)
    List<Integer> getListOpportunityCountPerAccount();

}
