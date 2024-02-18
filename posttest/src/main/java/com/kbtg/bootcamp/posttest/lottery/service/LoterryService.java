package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketsDto;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoterryService {
    private final LotteryRepository lotteryRepository;
    public LoterryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public TicketDto save(Lottery lottery) {
        Optional<Lottery> optional = lotteryRepository.findById(lottery.getTicket());
        if(optional.isPresent()) {
            throw new RuntimeException("Duplicate lottery");
        }else {
            return new TicketDto(lotteryRepository.save(lottery).getTicket());
        }
    }

    public TicketsDto findAll() {
        return new TicketsDto(lotteryRepository.findAll()
                .stream()
                .map(l -> l.getTicket()).
                collect(Collectors.toList()));
    }
}
