package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.exception.AlreadyExistedException;
import com.kbtg.bootcamp.posttest.models.lottery.Lottery;
import com.kbtg.bootcamp.posttest.models.lottery.LotteryRequestDTO;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final LotteryRepository lotteryRepository;

    public AdminService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public TicketIdResponseDTO addLotteries(LotteryRequestDTO lotteryRequestDTO){
        Optional<Lottery> lottery = lotteryRepository.findByTicket(lotteryRequestDTO.getTicket());
        if(lottery.isPresent()){
            throw new AlreadyExistedException("Lottery with the same ticket code is already recorded.");
        }
        else{
            Lottery newLottery = new Lottery(lotteryRequestDTO.getTicket(), lotteryRequestDTO.getPrice(), lotteryRequestDTO.getAmount());
            Lottery savedLottery = lotteryRepository.save(newLottery);
            return new TicketIdResponseDTO(savedLottery.getTicket());
        }

    }
}
