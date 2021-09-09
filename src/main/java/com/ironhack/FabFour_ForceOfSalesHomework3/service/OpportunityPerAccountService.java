package com.ironhack.FabFour_ForceOfSalesHomework3.service;

import com.ironhack.FabFour_ForceOfSalesHomework3.repository.OpportunityPerAccountRepository;
import com.ironhack.FabFour_ForceOfSalesHomework3.service.interfaces.IAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityPerAccountService implements IAggregateService {

    @Autowired
    OpportunityPerAccountRepository opportunityPerAccountRepository;

    @Override
    public Double getMeanValue() {
        return opportunityPerAccountRepository.getMeanOpportunityCountPerAccount();
    }

    @Override
    public Integer getMaxValue() {
        return opportunityPerAccountRepository.getMaxOpportunityCountPerAccount();
    }

    @Override
    public Integer getMinValue() {
        return opportunityPerAccountRepository.getMinOpportunityCountPerAccount();
    }

    @Override
    public Double getMedianValue() {
        Optional<List<Integer>> opportunityCountPerAccountList = Optional.of(opportunityPerAccountRepository.getListOpportunityCountPerAccount());

        List<Integer> opportunityCountPerAccount = opportunityCountPerAccountList.get();

        Collections.sort(opportunityCountPerAccount);

        if(opportunityCountPerAccount.size() %2 == 0) {

            int middleIndex = opportunityCountPerAccount.size()/2;

            System.out.println("size "+opportunityCountPerAccount.size());
            System.out.println();

            return (((double) opportunityCountPerAccount.get(middleIndex) + (double) opportunityCountPerAccount.get(middleIndex - 1)) / 2);

        } else return (double) opportunityCountPerAccount.get((opportunityCountPerAccount.size()-1)/2);
    }
}
