package com.kbtg.bootcamp.posttest.lottery.service.impl;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.exception.InternalServerErrorException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.response.TicketResponse;
import com.kbtg.bootcamp.posttest.lottery.service.LotteryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryServiceImpl implements LotteryService {

  private final LotteryRepository lotteryRepository;

  @Autowired
  public LotteryServiceImpl(LotteryRepository lotteryRepository) {
    this.lotteryRepository = lotteryRepository;
  }

  @Override
  public String createLottery(CreateLotteryRequest createLotteryRequest) throws Exception {
    try {
      Lottery lottery = new Lottery();
      lottery.setTicket(createLotteryRequest.getTicket());
      lottery.setPrice(createLotteryRequest.getPrice());
      lottery.setAmount(createLotteryRequest.getAmount());
      Lottery lotteryCreated = lotteryRepository.save(lottery);
      return lotteryCreated.getTicket();
    } catch (Exception e) {
      throw new InternalServerErrorException("Create lottery failed");
    }
  }

  @Override
  public TicketResponse getAllTickets() throws InternalServerErrorException {
    try {
      List<Lottery> lotteries = lotteryRepository.findAll();
      TicketResponse ticketResponse = new TicketResponse();
      ticketResponse.setTickets(lotteries.stream().map(Lottery::getTicket).toList());
      return ticketResponse;
    } catch (Exception e) {
      throw new InternalServerErrorException("Get all tickets failed");
    }
  }

  @Override
  public Boolean checkAmountLottery(String ticket) throws NotFoundException {
    Lottery lottery = lotteryRepository.findByTicket(ticket)
        .orElseThrow(() -> new NotFoundException("Lottery not found with ticket: " + ticket));
    return lottery.getAmount() > 0;
  }

  @Override
  public Boolean checkExistTicket(String ticket) {
    return lotteryRepository.existsByTicket(ticket);
  }
}
