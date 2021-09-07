package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT AVG(cast(EMPLOYEE_COUNT AS DOUBLE)) as meanEmployeeCount FROM ACCOUNT", nativeQuery = true)
    Double getMeanEmployeeCount();
    @Query(value = "SELECT MAX(EMPLOYEE_COUNT) as meanEmployeeCount FROM ACCOUNT", nativeQuery = true)
    Integer getMaxEmployeeCount();
    @Query(value = "SELECT MIN(EMPLOYEE_COUNT) as meanEmployeeCount FROM ACCOUNT", nativeQuery = true)
    Integer getMinEmployeeCount();
    @Query(value = "SELECT EMPLOYEE_COUNT FROM ACCOUNT", nativeQuery = true)
    List<Integer> getEmployeeCountList();
}
