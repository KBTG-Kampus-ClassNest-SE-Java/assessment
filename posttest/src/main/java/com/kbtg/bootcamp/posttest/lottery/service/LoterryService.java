package com.kbtg.bootcamp.posttest.lottery.service;

import com.kbtg.bootcamp.posttest.lottery.dto.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketListResponseDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketResponseDto;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoterryService {
    private final LotteryRepository lotteryRepository;

    public LoterryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public TicketResponseDto save(LotteryRequestDto lotteryRequestDto) {
        Optional<Lottery> optional = lotteryRepository.findById(lotteryRequestDto.getTicket());
        if (optional.isPresent()) {
            throw new RuntimeException("Duplicate lottery");
        } else {
            return new TicketResponseDto(lotteryRepository.save(Lottery.builder()
                    .amount(lotteryRequestDto.getAmount())
                    .price(lotteryRequestDto.getPrice())
                    .ticket(lotteryRequestDto.getTicket())
                    .build()
            ).getTicket());
        }
    }

    public TicketListResponseDto findAll() {
        return new TicketListResponseDto(lotteryRepository.findAll()
                .stream()
                .map(l -> l.getTicket()).
                collect(Collectors.toList()));
    }
}
