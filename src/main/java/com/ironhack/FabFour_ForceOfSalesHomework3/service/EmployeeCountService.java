package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.repository.EmployeeCountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.interfaces.IAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeCountService implements IAggregateService {

    @Autowired
    EmployeeCountRepository employeeCountRepository;

    @Override
    public Double getMeanValue() {
        return employeeCountRepository.getMeanEmployeeCount();
    }

    @Override
    public Integer getMaxValue() {
        return employeeCountRepository.getMaxEmployeeCount();
    }

    @Override
    public Integer getMinValue() {
        return employeeCountRepository.getMinEmployeeCount();
    }

    @Override
    public Double getMedianValue() {
        Optional<List<Integer>> employeeCountList = Optional.of(employeeCountRepository.getEmployeeCountList());

        List<Integer> employeeCounts = employeeCountList.get();

        Collections.sort(employeeCounts);

        if(employeeCounts.size() %2 == 0) {

            int middleIndex = employeeCounts.size()/2;

            return (((double)employeeCounts.get(middleIndex) + (double)employeeCounts.get(middleIndex - 1)) / 2);

        } else return (double) employeeCounts.get((employeeCounts.size()-1)/2);
    }
}
