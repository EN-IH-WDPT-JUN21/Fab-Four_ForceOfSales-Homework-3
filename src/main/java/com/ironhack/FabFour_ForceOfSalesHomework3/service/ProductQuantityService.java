package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.repository.ProductQuantityRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.interfaces.IAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductQuantityService implements IAggregateService {

    @Autowired
    ProductQuantityRepository productQuantityRepository;

    @Override
    public Double getMeanValue() {
        return productQuantityRepository.getMeanProductQuantity();
    }

    @Override
    public Integer getMaxValue() {
        return productQuantityRepository.getMaxProductQuantity();
    }

    @Override
    public Integer getMinValue() {
        return productQuantityRepository.getMinProductQuantity();
    }

    @Override
    public Double getMedianValue() {
        Optional<List<Integer>> employeeCountList = Optional.of(productQuantityRepository.getProductQuantityList());

        List<Integer> productQuantity = employeeCountList.get();

        Collections.sort(productQuantity);

        if(productQuantity.size() %2 == 0) {

            int middleIndex = productQuantity.size()/2;

            return (((double)productQuantity.get(middleIndex) + (double) productQuantity.get(middleIndex - 1)) / 2);

        } else return (double) productQuantity.get((productQuantity.size()-1)/2);
    }
}
