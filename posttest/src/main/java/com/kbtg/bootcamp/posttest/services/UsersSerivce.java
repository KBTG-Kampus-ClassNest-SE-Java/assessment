package com.kbtg.bootcamp.posttest.services;

import com.kbtg.bootcamp.posttest.models.lottery.Lottery;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdListResponseDTO;
import com.kbtg.bootcamp.posttest.models.lottery.TicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.models.userTicket.UserTicket;
import com.kbtg.bootcamp.posttest.models.userTicket.UserTicketIdResponseDTO;
import com.kbtg.bootcamp.posttest.models.userTicket.UserTicketInfoResponseDTO;
import com.kbtg.bootcamp.posttest.repositories.LotteryRepository;
import com.kbtg.bootcamp.posttest.repositories.UserTickerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersSerivce {

    private final LotteryRepository lotteryRepository;
    private final UserTickerRepository userTickerRepository;

    public UsersSerivce(LotteryRepository lotteryRepository, UserTickerRepository userTickerRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTickerRepository = userTickerRepository;
    }


    public TicketIdListResponseDTO getBuyAbleLotteries(){
        List<Lottery> lotteries = lotteryRepository.findLotteryWithAmountGreaterThanUserTicketsCount();
        List<String> ticketIds = lotteries.stream().map(Lottery::getTicket).toList();
        return new TicketIdListResponseDTO(ticketIds);
    }

    public UserTicketIdResponseDTO buyTicket(String userId, String ticketId){
        Optional<Lottery> optionalLottery = lotteryRepository.findByTicket(ticketId);
        if(optionalLottery.isPresent()){
            Lottery lottery = optionalLottery.get();
            if(lottery.getUserTickets().size() < lottery.getAmount()){
                UserTicket userTicket = new UserTicket(userId, lottery);
                UserTicket savedUserTicket = userTickerRepository.save(userTicket);
                return new UserTicketIdResponseDTO(savedUserTicket.getId().toString());
            }
            else {
                throw new NoSuchElementException("Lottery with this ticket ID is out of stock.");
            }
        }
        else {
            throw new NoSuchElementException("Lottery with this ticket ID is not available.");
        }

    }

    public UserTicketInfoResponseDTO getAllUserLotteriesInfo(String userId){
        List<UserTicket> userTicketList = userTickerRepository.findByUserId(userId);
        Integer count = userTicketList.size();
        Double totalPrice = userTicketList.stream()
                .mapToDouble(userTicket -> userTicket.getLottery().getPrice())
                .sum();
        List<String> tickets = userTicketList.stream()
                .map(userTicket -> userTicket.getLottery().getTicket())
                .collect(Collectors.toList());
        tickets.sort(Comparator.naturalOrder());

        return new UserTicketInfoResponseDTO(tickets, count, totalPrice );
    }



    public TicketIdResponseDTO sellUserTicket(String userId, String ticketId
    ){
        Integer deleteCount = userTickerRepository.deleteByUserIdAndLotteryTicket(userId, ticketId);
        if(deleteCount > 0){
            return new TicketIdResponseDTO(ticketId);
        }else {
            throw new NoSuchElementException("No lottery to sell for this user.");
        }
    }
}
