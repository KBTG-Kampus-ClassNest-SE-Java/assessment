package com.kbtg.bootcamp.posttest.user.service.impl;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.app.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.app.exception.IllegalArgumentException;
import com.kbtg.bootcamp.posttest.app.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import com.kbtg.bootcamp.posttest.lottery.responese.UserLotteryResponse;
import com.kbtg.bootcamp.posttest.user.dto.TicketIdResponse;
import com.kbtg.bootcamp.posttest.user.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final LotteryRepository lotteryRepository;

  private final UserTicketRepository userTicketRepository;

  public UserServiceImpl(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
    this.lotteryRepository = lotteryRepository;
    this.userTicketRepository = userTicketRepository;
  }

  public void validateUserId(String userId) {
    if (userId.length() != 10 || !userId.matches("\\d+")) {
      throw new IllegalArgumentException("User ID must be a 10-digit number.");
    }
  }
  public void validateTicketId(String ticketId) {
    if (ticketId.length() != 6 || !ticketId.matches("\\d+")){
      throw new IllegalArgumentException("Ticket ID must be a 6-digit number.");
    }
  }
  @Override
  @Transactional
  public TicketIdResponse buyLottery(String ticketId, String userId) {
    validateUserId(userId);
    validateTicketId(ticketId);

    Lottery lottery = lotteryRepository.findByTicket(ticketId)
        .orElseThrow(() -> new NotFoundException("Lottery not found"));

    if (lottery.getAmount() <= 0) {
      throw new BadRequestException("Lottery is sold out");
    }
    if (userTicketRepository.existsByUserIdAndLotteryId(Integer.parseInt(userId),
        lottery.getId())) {
      throw new BadRequestException("User already bought this lottery");
    }

    try {
      UserTicket userTicket = userTicketRepository.save(UserTicket.builder()
          .userId(Integer.valueOf(userId))
          .lottery(lottery)
          .build());
      lottery.setAmount(lottery.getAmount() - 1);
      lotteryRepository.save(lottery);
      return new TicketIdResponse(userTicket.getId().toString());
    } catch (Exception e) {
      throw new BadRequestException("Failed to buy lottery: " + e.getMessage());
    }
  }

  @Override
  public UserLotteryResponse getLotteriesByUserId(String userId) {
    validateUserId(userId);

    List<Lottery> tickets = userTicketRepository.findByUserId(Integer.parseInt(userId))
        .stream()
        .map(UserTicket::getLottery)
        .toList();

    List<String> lotteryTickets = tickets.stream().map(Lottery::getTicket).collect(Collectors.toList());

    int count = lotteryTickets.size();

    int cost = tickets.stream().mapToInt(Lottery::getPrice).sum();
    return new UserLotteryResponse(lotteryTickets,count,cost);
  }

  @Override
  @Transactional
  public TicketResponse sellLottery(String ticketId, String userId) {
    validateUserId(userId);
    validateTicketId(ticketId);

    Lottery lottery = lotteryRepository.findByTicket(ticketId)
        .orElseThrow(() -> new NotFoundException("Lottery not found"));

    UserTicket userTicket = userTicketRepository.findByUserIdAndLotteryId(Integer.parseInt(userId), lottery.getId()
    ).orElseThrow(() -> new IllegalArgumentException("User did not buy this lottery"));

    userTicketRepository.delete(userTicket);
    lottery.setAmount(lottery.getAmount() + 1);
    return new TicketResponse(userTicket.getLottery().getTicket());
  }


}
