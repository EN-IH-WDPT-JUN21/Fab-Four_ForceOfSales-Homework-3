package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //Report by country
    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM account " +
                    "INNER JOIN opportunity ON opportunity.account_id = account.id " +
                    "WHERE account.country = :country",
            nativeQuery = true
    )
    long countOpportunitiesByCountry(@Param("country") String country);

    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "INNER JOIN account ON opportunity.account_id = account.id " +
                    "WHERE account.country = :country AND opportunity.status = :status",
            nativeQuery = true
    )
    long countOpportunitiesByCountryStatus(@Param("country") String country, @Param("status") String status);

    //Report by City
    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "INNER JOIN account ON opportunity.account_id = account.id " +
                    "WHERE account.city = :city",
            nativeQuery = true
    )
    long countOpportunitiesByCity(@Param("city") String city);

    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "INNER JOIN account ON opportunity.account_id = account.id " +
                    "WHERE account.city = :city AND opportunity.status = :status",
            nativeQuery = true
    )
    long countOpportunitiesByCityStatus(@Param("city") String city, @Param("status") String status);

    //Report by Industry
    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "INNER JOIN account ON opportunity.account_id = account.id " +
                    "WHERE account.industry = :industry",
            nativeQuery = true
    )
    long countOpportunitiesByIndustry(@Param("industry") String industry);

    @Query(
            value = "SELECT COUNT(opportunity.id) " +
                    "FROM opportunity " +
                    "INNER JOIN account ON opportunity.account_id = account.id " +
                    "WHERE account.industry = :industry AND opportunity.status = :status",
            nativeQuery = true
    )
    long countOpportunitiesByIndustryStatus(@Param("industry") String country, @Param("status") String status);

}
