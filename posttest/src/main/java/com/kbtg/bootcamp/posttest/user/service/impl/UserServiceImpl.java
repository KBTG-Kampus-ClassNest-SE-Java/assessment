package com.kbtg.bootcamp.posttest.user.service.impl;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.entity.User;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.response.GetAllTicketResponse;
import com.kbtg.bootcamp.posttest.user.repository.UserRepository;
import com.kbtg.bootcamp.posttest.user.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.user.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final LotteryRepository lotteryRepository;
  private final UserRepository userRepository;
  private final UserTicketRepository userTicketRepository;

  @Autowired
  public UserServiceImpl(LotteryRepository lotteryRepository, UserRepository userRepository,
      UserTicketRepository userTicketRepository) {
    this.lotteryRepository = lotteryRepository;
    this.userRepository = userRepository;
    this.userTicketRepository = userTicketRepository;
  }

  @Override
  public User validateUser(Long userId) throws NotFoundException {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
  }

  @Override
  @Transactional
  public Long buyTicketByUserId(Long userId, String ticket) throws Exception {
    User user = validateUser(userId);

    Lottery lottery = lotteryRepository.findByTicket(ticket)
        .orElseThrow(() -> new NotFoundException("Ticket not found with ticket: " + ticket));
    lottery.setAmount(0);

    UserTicket userTicket = new UserTicket();
    userTicket.setUser(user);
    userTicket.setLottery(lottery);

    lotteryRepository.save(lottery);
    UserTicket userTicketCreated = userTicketRepository.save(userTicket);

    return userTicketCreated.getId();
  }

  @Override
  @Transactional
  public String deleteUserTicket(Long userId, String ticket) throws Exception {
    User user = validateUser(userId);
    Lottery lottery = lotteryRepository.findByTicket(ticket)
        .orElseThrow(() -> new NotFoundException("Ticket not found with ticket: " + ticket));
    lottery.setAmount(1);

    userTicketRepository.deleteByUserAndLottery(user, lottery);
    Lottery lotteryUpdated = lotteryRepository.save(lottery);

    return lotteryUpdated.getTicket();
  }

  @Override
  public GetAllTicketResponse getAllTicketsByUserId(Long userId) throws Exception {
    User user = validateUser(userId);

    List<UserTicket> userTickets = userTicketRepository.findAllByUser(user);
    GetAllTicketResponse getAllTicketResponse = new GetAllTicketResponse();

    List<String> tickets = userTickets.stream().map(UserTicket::getLottery).map(Lottery::getTicket)
        .toList();
    int cost = userTickets.stream().map(UserTicket::getLottery).map(Lottery::getPrice)
        .reduce(0, Integer::sum);
    int count = userTickets.size();

    return new GetAllTicketResponse(tickets, count, cost);
  }
}
