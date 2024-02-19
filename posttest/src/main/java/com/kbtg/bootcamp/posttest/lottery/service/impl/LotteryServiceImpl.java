package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.IllegalArgumentException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.responese.LotteryListResponse;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import com.kbtg.bootcamp.posttest.lottery.responese.UserLotteryResponse;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.user.repository.UserTicketRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;

@Service
public class LotteryServiceImpl implements LotteryService {

  private final LotteryRepository lotteryRepository;
  private final UserTicketRepository userTicketRepository;


  public LotteryServiceImpl(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
    this.lotteryRepository = lotteryRepository;
    this.userTicketRepository = userTicketRepository;
  }

  public void validateUserId(String userId) {
    if (userId.length() != 10 || !userId.matches("\\d+")) {
      throw new IllegalArgumentException("User ID must be a 10-digit number.");
    }
  }

  @Override
  public TicketResponse createLottery(CreateLotteryRequest createLotteryRequest) {
    lotteryRepository.findDistinctByTicket(createLotteryRequest.getTicket()).ifPresent(
        lottery -> {
          throw new BadRequestException("Lottery already exists");
        }
    );
    try {
      Lottery newLottery = Lottery.builder()
          .ticket(createLotteryRequest.getTicket())
          .price(createLotteryRequest.getPrice())
          .amount(createLotteryRequest.getAmount())
          .build();
      Lottery savedLottery = lotteryRepository.save(newLottery);
      return new TicketResponse(savedLottery.getTicket());
    } catch (Exception e) {
      throw new BadRequestException("Failed to create lottery: " + e.getMessage());
    }
  }

  @Override
  public LotteryListResponse getLotteries() {
    List<String> tickets = lotteryRepository.findAll()
        .stream()
        .map(Lottery::getTicket)
        .collect(Collectors.toList());
    return new LotteryListResponse(tickets);
  }

  @Override
  @Transactional
  public TicketResponse buyLottery(int ticketId, int userId) {

    validateUserId(String.valueOf(userId));

    Lottery lottery = lotteryRepository.findById(ticketId)
        .orElseThrow(() -> new NotFoundException("Lottery not found"));

    if (lottery.getAmount() <= 0) {
      throw new BadRequestException("Lottery is sold out");
    }
    if (userTicketRepository.existsByUserIdAndLotteryId(userId, ticketId)) {
      throw new BadRequestException("User already bought this lottery");
    }

    try {
      UserTicket userTicket = userTicketRepository.save(UserTicket.builder()
          .userId(userId)
          .lottery(lottery)
          .build());

      lottery.setAmount(lottery.getAmount() - 1);
      lotteryRepository.save(lottery);
      return new TicketResponse(userTicket.getId().toString());
    } catch (Exception e) {
      throw new BadRequestException("Failed to buy lottery: " + e.getMessage());
    }
  }

  @Override
  public UserLotteryResponse getLotteriesByUserId(int userId) {

    validateUserId(String.valueOf(userId));

    List<Lottery> tickets = userTicketRepository.findByUserId(userId)
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
  public TicketResponse sellLottery(int ticketId, int userId) {

    validateUserId(String.valueOf(userId));

    Lottery lottery = lotteryRepository.findById(ticketId)
        .orElseThrow(() -> new NotFoundException("Lottery not found"));

    UserTicket userTicket = userTicketRepository.findByUserIdAndLotteryId(userId, ticketId)
        .orElseThrow(() -> new NotFoundException("User ticket not found"));

    userTicketRepository.delete(userTicket);
    lottery.setAmount(lottery.getAmount() + 1);
    return new TicketResponse(userTicket.getId().toString());
  }
}
