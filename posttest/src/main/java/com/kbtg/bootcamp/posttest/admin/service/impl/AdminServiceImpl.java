package com.kbtg.bootcamp.posttest.admin.service.impl;


import com.kbtg.bootcamp.posttest.admin.service.AdminService;
import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.app.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.admin.request.CreateLotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.responese.TicketResponse;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  private final LotteryRepository lotteryRepository;

  public AdminServiceImpl(LotteryRepository lotteryRepository) {
    this.lotteryRepository = lotteryRepository;
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


}
