package com.kbtg.bootcamp.posttest.userticket.service;


import com.kbtg.bootcamp.posttest.lottery.entity.Lottery;
import com.kbtg.bootcamp.posttest.lottery.repo.LotteryRepo;
import com.kbtg.bootcamp.posttest.lottery.rest.dto.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.userticket.entity.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.repo.UserTicketRepo;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketReqDto;
import com.kbtg.bootcamp.posttest.userticket.rest.dto.UserTicketResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTicketServiceImp implements  UserTicketService {
    private final UserTicketRepo userTicketRepo;
    private final LotteryRepo lotteryRepo;

    @Autowired
    public UserTicketServiceImp(UserTicketRepo userTicketRepo, LotteryRepo lotteryRepo) {
        this.userTicketRepo = userTicketRepo;
        this.lotteryRepo = lotteryRepo;
    }
    @Override
    public UserTicketReqDto buyLottery(String userId, String ticketId) {

        Optional<Lottery> lottery = lotteryRepo.findById(ticketId);
        UserTicket userTicket = new UserTicket();

        if (lottery.isEmpty()) {
            throw new RuntimeException("Lottery number not found.");
        }

        lottery.get().setTicket(ticketId);
        userTicket.setUserId(userId);
        userTicket.setLottery(lottery.get());

        return new UserTicketReqDto(userTicketRepo.save(userTicket).getId());
    }

    @Override
    public UserTicketResDto getLotteryById(String userId) {
        UserTicketResDto userTicketResDto = new UserTicketResDto();
        List<UserTicket> fromUser = userTicketRepo.findByUserId(userId);
        List<String> tickets = fromUser.stream().map(eachUserId -> eachUserId.getLottery().getTicket()).toList();

        userTicketResDto.setTickets(tickets);
        userTicketResDto.setCount(tickets.size());
        userTicketResDto.setTotalPrice(sumPrice(fromUser));

        return userTicketResDto;

    }



    @Override
    public LotteryResponseDto sellLottery(String userId, String ticketId) {
        List<UserTicket> byUser = userTicketRepo.findByUserIdAndTicketId(userId, ticketId);
        if (!byUser.isEmpty()) {
            userTicketRepo.delete(byUser.get(0));
            Optional<Lottery> optional = lotteryRepo.findById(ticketId);
            if (optional.isPresent()) {
                Lottery lottery = optional.get();
                lottery.setAmount(1);
                lotteryRepo.save(lottery);
                return new LotteryResponseDto(ticketId);
            } else {
                throw new InvalidUserTicketException("Invalid userId or ticketId");
            }


        }
    }
    private Integer sumPrice(List<UserTicket> fromUser) {
        Integer sum = 0;
        for(UserTicket ticket : fromUser){
            sum += ticket.getLottery().getPrice();
        }
        return sum;
    }



//----------------------------------------
//    public UserTicketsRequestDto buyLotteries(String userId, String ticketId) {
//        UserTicket userTicket = new UserTicket();
//
//        Optional<Lottery> optionalLottery = lotteryRepository.findById((ticketId));
//        Lottery lottery;
//
//        if (optionalLottery.isEmpty()) {
//            throw new LotteryUnavailableException("Lottery Unavailable Exception");
//        } else {
//            lottery = optionalLottery.get();
//            if (lottery.getAmount() <= 0) {
//                throw new LotteryUnavailableException("Lottery Unavailable Exception");
//            }
//
//        }
//        lottery.setTicket(ticketId);
//
//        userTicket.setUserId(userId);
//        userTicket.setLottery(lottery);
//
//        UserTicket saved = userTicketRepository.save(userTicket);
//        return new UserTicketsRequestDto(saved.getId());
//    }
//---------------------------------------------------------------------------
//    public UserTicketResponseDto getLotteriesByUserId(String userId) {
//        UserTicketResponseDto userTicketResponseDto = new UserTicketResponseDto();
//
//        List<UserTicket> byUser = userTicketRepository.findByUserId(userId);
//        List<String> tickets = byUser.stream().map(b -> b.getLottery().getTicket()).toList();
//
//        userTicketResponseDto.setTickets(tickets);
//        userTicketResponseDto.setCount(tickets.size());
//        userTicketResponseDto.setTotalPrice(sumAllPrice(byUser));
//
//        return userTicketResponseDto;
//    }
//-------------------------------------------------------------------
//    private Integer sumAllPrice(List<UserTicket> byUser) {
//        Integer sum = 0;
//        for(UserTicket ticket : byUser){
//            sum += ticket.getLottery().getPrice();
//        }
//        return sum;
//    }
//--------------------------------------------------------------------------------
//
//    public LotteryResponseDto deleteLotteriesByUserId(String userId, String ticketId) {
//        List<UserTicket> byUser = userTicketRepository.findByUserIdAndTicketId(userId, ticketId);
//        if (!byUser.isEmpty()) {
//            userTicketRepository.delete(byUser.get(0));
//            Optional<Lottery> optional = lotteryRepository.findById(ticketId);
//            if(optional.isPresent()) {
//                Lottery lottery = optional.get();
//                lottery.setAmount(1);
//                lotteryRepository.save(lottery);
//                return new LotteryResponseDto(ticketId);
//            }else{
//                throw new InvalidUserTicketException("Invalid userId or ticketId");
//            }
//        } else {
//            throw new InvalidUserTicketException("Invalid userId or ticketId");
//        }
//    }
}
