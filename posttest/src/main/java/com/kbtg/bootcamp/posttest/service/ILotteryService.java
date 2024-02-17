package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ILotteryService implements LotteryService{

    private final LotteryRepository lotteryRepository;

    public ILotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Override
    public List<LotteryEntity> findAllLottery() {
        return lotteryRepository.findAll();
    }

    @Override
    public Optional<LotteryEntity> findLotteryById(Long id) {
        return lotteryRepository.findById(id);
    }

    @Override
    public LotteryEntity saveLottery(LotteryEntity lotteryEntity) {
        return lotteryRepository.save(lotteryEntity);
    }

    @Override
    public LotteryEntity updateLottery(LotteryEntity lotteryEntity) {
        return lotteryRepository.save(lotteryEntity);
    }

    @Override
    public void deleteLottery(Long id) {
        lotteryRepository.deleteById(id);
    }
}
