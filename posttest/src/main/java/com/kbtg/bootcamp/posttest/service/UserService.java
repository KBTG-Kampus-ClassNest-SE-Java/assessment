package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dao.LotteryRepository;
import com.kbtg.bootcamp.posttest.dao.UserTicketRepository;
import com.kbtg.bootcamp.posttest.dto.ProductResponseDTO;
import com.kbtg.bootcamp.posttest.dto.UserResponseDto;
import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketEntity;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserTicketRepository userTicketRepository;

    @Autowired
    private LotteryRepository lotteryRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto buyLotteryTicketProcess(String userId, String ticketId) throws Exception {

        LotteryEntity lottery = this.lotteryRepository.findById(Long.valueOf(ticketId))
                .filter(lotteryEntity -> ObjectUtils.isEmpty(lotteryEntity.getUserId()))
                .orElseThrow(() -> new NotFoundException("No such lottery ticket with id: " + ticketId));

        UserTicketEntity userTicketCheck = this.userTicketRepository.findByUserId(Long.valueOf(userId));
        if (userTicketCheck == null) {
            userTicketCheck = new UserTicketEntity();
            userTicketCheck.setUserId(Long.valueOf(userId));
        }

        BigDecimal totalPrice = Optional.ofNullable(userTicketCheck.getTotalPrice()).orElse(BigDecimal.ZERO);
        totalPrice = totalPrice.add(lottery.getPrice());
        userTicketCheck.setTotalPrice(totalPrice);

        lottery.setUserId(Long.valueOf(userId));
        this.userTicketRepository.save(userTicketCheck);
        this.lotteryRepository.save(lottery);
        return new UserResponseDto(lottery.getId().toString());
    }


    public ProductResponseDTO getAllMyTicketsProcess(String userId) {

        UserTicketEntity userTicket = userTicketRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotFoundException("No such User with id: " + userId));

        List<LotteryEntity> lottery = lotteryRepository.findByUserId(Long.valueOf(userId));

        List<String> tickets = lottery.stream()
                .map(LotteryEntity::getTicketNumber)
                .collect(Collectors.toList());

        return new ProductResponseDTO(tickets,null, userTicket.getTotalPrice().toString());
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDTO deleteLotteryTicketProcess(String userId, String ticketId) {
        LotteryEntity lottery = this.lotteryRepository.findByIdAndUserId(Long.valueOf(ticketId),Long.valueOf(userId));
        if(ObjectUtils.isEmpty(lottery)){
            throw new NotFoundException("Not Found Data!");
        }
        minusTotalPriceUser(userId, lottery.getPrice());
        this.lotteryRepository.deleteById(Long.valueOf(ticketId));

        return new ProductResponseDTO(null,lottery.getTicketNumber(),null);
    }

    @Transactional(rollbackFor = Exception.class)
    private void minusTotalPriceUser(String userId, BigDecimal price) {
        UserTicketEntity userTicket = this.userTicketRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new BadRequestException("No such User with id: " + userId));

        BigDecimal totalPrice = userTicket.getTotalPrice().subtract(price);
        userTicket.setTotalPrice(totalPrice);
        this.userTicketRepository.saveAndFlush(userTicket);
    }

}
