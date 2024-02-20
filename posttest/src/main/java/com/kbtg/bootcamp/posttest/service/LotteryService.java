package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LotteryService implements ImpLotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
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
    public void deleteLottery(Long id) {
        lotteryRepository.deleteById(id);
    }

    @Override
    public void updateLottery(String ticketId, boolean active) {
        lotteryRepository.updateLottery(ticketId, active);
    }
}
