package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LotteryService {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Transactional
    public List<Lottery> getLottery(){
       List<Lottery> lotto = lotteryRepository.findAll();
        return lotto;
    }

}
