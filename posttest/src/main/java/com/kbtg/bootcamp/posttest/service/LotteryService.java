package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.service.impl.ImpLotteryService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LotteryService implements ImpLotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Override  //todo USE BY ADMIN for list all lottery that exist, no consider where is it now
    public List<LotteryEntity> getAllLottery() {
        return lotteryRepository.findAll();
    }

    @Override  //todo USE BY ADMIN for add lottery to the store
    public LotteryEntity addLotteryToStore(LotteryEntity lotteryEntity) {
        return lotteryRepository.save(lotteryEntity);
    }

    @Override  //todo USE BY ADMIN for list all lottery that still available in the shop
    public List<LotteryEntity> getRemainLotteryFromStore() {
        return lotteryRepository.getRemainLotteryFromStore();
    }

//    @Override  //todo USE BY USER refund lottery to store
//    public void refundLotteryToStore(Long id) {
//        lotteryRepository.deleteById(id);
//    }

    @Override  //todo USE FOR STATUS CHECK THAT IS ALREADY BOUGHT OR NOT
    public void updateStatusLottery(String ticketId, boolean status) {
        lotteryRepository.updateStatusLottery(ticketId, status);
    }
//    public int updateStatusLottery(String ticketId, boolean status)
//    return lotteryRepository.updateLottery(ticketId, status);
}
