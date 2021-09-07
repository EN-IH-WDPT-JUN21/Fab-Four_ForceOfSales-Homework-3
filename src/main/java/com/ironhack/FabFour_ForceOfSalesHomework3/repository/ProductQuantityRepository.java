package com.ironhack.FabFour_ForceOfSalesHomework3.repository;

import com.ironhack.FabFour_ForceOfSalesHomework3.dao.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductQuantityRepository extends JpaRepository<Opportunity, Long> {

    @Query(value = "SELECT AVG(cast(quantity AS DOUBLE)) as quantityCount FROM OPPORTUNITY", nativeQuery = true)
    Double getMeanProductQuantity();
    @Query(value = "SELECT MAX(quantity) as quantityCount FROM OPPORTUNITY", nativeQuery = true)
    Integer getMaxProductQuantity();
    @Query(value = "SELECT MIN(quantity) as quantityCount FROM OPPORTUNITY", nativeQuery = true)
    Integer getMinProductQuantity();
    @Query(value = "SELECT quantity FROM OPPORTUNITY", nativeQuery = true)
    List<Integer> getProductQuantityList();
}
