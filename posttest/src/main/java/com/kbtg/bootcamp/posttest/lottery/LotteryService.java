package com.kbtg.bootcamp.posttest.lottery;


import com.kbtg.bootcamp.posttest.exception.LotteryException;
import com.kbtg.bootcamp.posttest.userTicket.UserTicket;
import com.kbtg.bootcamp.posttest.userTicket.UserTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LotteryService {
    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;

    public LotteryService(LotteryRepository lotteryRepository,UserTicketRepository userTicketRepository) {
        this.userTicketRepository = userTicketRepository;
        this.lotteryRepository = lotteryRepository;
    }

    @Transactional
    public void addLottery(LotteryRequest lotteryRequest){
        Lottery ticket = new Lottery(   lotteryRequest.ticket(),
                                        lotteryRequest.price(),
                                        lotteryRequest.amount()
                                    );
        lotteryRepository.save(ticket);
    }
    @Transactional
    public String buyLottery(String userId,String ticketId){
        Optional<Lottery>  optionalLottery = lotteryRepository.findById(ticketId);

        if(optionalLottery.isEmpty())
            throw new LotteryException("ticket" + ticketId + "does not exist");

        Lottery lottery = optionalLottery.get();
        int totalAmount = lottery.getAmount();

        if(totalAmount<1)
            throw new LotteryException("ticket" + ticketId + "out of stock");

        lottery.setAmount(totalAmount-1);
        lotteryRepository.save(lottery);

        UserTicket userTicket = new UserTicket(     userId,
                                                    lottery.getPrice(),
                                                   1
                                                );

        userTicket.setLottery(lottery);
        userTicketRepository.save(userTicket);
        return Integer.toString(userTicket.getId()) ;

    }

    public List<Lottery> getAllLotteries(){
        return lotteryRepository.findAll();
    }

    public List<Lottery> getAllAvailableLotteries(){
        return lotteryRepository.findAllAvailableLotteries();
    }
}
