package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /*
    * A count of all Opportunities by country can be displayed by typing “Report Opportunity by Country”
A count of all CLOSED_WON Opportunities by country can be displayed by typing “Report CLOSED-WON by Country”
A count of all CLOSED_LOST Opportunities by country can be displayed by typing “Report CLOSED-LOST by Country”
A count of all OPEN Opportunities by country can be displayed by typing “Report OPEN by Country”


    A count of all Opportunities by the city can be displayed by typing “Report Opportunity by City”
    A count of all CLOSED_WON Opportunities by the city can be displayed by typing “Report CLOSED-WON by City”
    A count of all CLOSED_LOST Opportunities by the city can be displayed by typing “Report CLOSED-LOST by City”
    A count of all OPEN Opportunities by the city can be displayed by typing “Report OPEN by City
    *
    * A count of all Opportunities by industry can be displayed by typing “Report Opportunity by Industry”
A count of all CLOSED_WON Opportunities by industry can be displayed by typing “Report CLOSED-WON by Industry”
A count of all CLOSED_LOST Opportunities by industry can be displayed by typing “Report CLOSED-LOST by Industry”
A count of all OPEN Opportunities by industry can be displayed by typing “Report OPEN by Industry”*/
}
