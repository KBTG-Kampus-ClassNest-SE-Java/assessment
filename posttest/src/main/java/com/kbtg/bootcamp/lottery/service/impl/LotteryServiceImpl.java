package com.kbtg.bootcamp.lottery.service.impl;

import com.kbtg.bootcamp.lottery.entity.Lottery;
import com.kbtg.bootcamp.lottery.entity.UserTicket;
import com.kbtg.bootcamp.lottery.entity.Users;
import com.kbtg.bootcamp.lottery.exception.NotFoundException;
import com.kbtg.bootcamp.lottery.repository.LotteryRepository;
import com.kbtg.bootcamp.lottery.repository.UserTicketRepository;
import com.kbtg.bootcamp.lottery.repository.UsersRepository;
import com.kbtg.bootcamp.lottery.request.LotteryRequestDto;
import com.kbtg.bootcamp.lottery.response.LotteryPurchaseResponseDto;
import com.kbtg.bootcamp.lottery.response.LotteryResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.kbtg.bootcamp.lottery.lib.Const.*;

@Service
public class LotteryServiceImpl implements LotteryService {
    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public LotteryServiceImpl(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository, UsersRepository usersRepository) throws Exception {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public LotteryResponseDto createLottery(LotteryRequestDto lotteryDTO) {
        Optional<Lottery> optionalLottery = lotteryRepository.findById(lotteryDTO.getTicketNumber());
        if (optionalLottery.isPresent()) {
            Lottery lottery = optionalLottery.get();
            int totalAmount;
            totalAmount = lotteryDTO.getTicketAmount() + lottery.getTicketAmount();
            lottery.setTicketPrice(lotteryDTO.getTicketPrice());
            lottery.setLastUpdate(LocalDateTime.now());
            lottery.setTicketAmount(totalAmount);
            lotteryRepository.save(optionalLottery.get());
            return new LotteryResponseDto(optionalLottery.get().getTicketNumber());
        }

        Lottery newLottery = new Lottery();
        newLottery.setTicketNumber(lotteryDTO.getTicketNumber());
        newLottery.setTicketPrice(lotteryDTO.getTicketPrice());
        newLottery.setTicketAmount(lotteryDTO.getTicketAmount());
        newLottery.setLastUpdate(LocalDateTime.now());
        lotteryRepository.save(newLottery);
        return new LotteryResponseDto(newLottery.getTicketNumber());
    }

    @Override
    public LotteryResponseDto getAllLotteryTickets() throws Exception {
        List<String> ticketNumbers = lotteryRepository.findAll().stream()
                .map(Lottery::getTicketNumber)
                .toList();
        return new LotteryResponseDto(ticketNumbers);
    }


    @Override
    @Transactional
    public LotteryPurchaseResponseDto purchaseLotteryTicket(final String userId, final String ticketNumber) {

        Optional<Lottery> optionalLottery = lotteryRepository.findById(ticketNumber);
        if (optionalLottery.isEmpty()) {
                throw new NotFoundException(String.format(LOTTERY_NOT_FOUND, ticketNumber));
        }
        Lottery lottery = optionalLottery.get();
        validateLotteryAvailable(lottery, ticketNumber);


        Optional<Users> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isEmpty()){
            createUser(userId);
        }

        decrementTicketAmount(lottery);
        final UserTicket userTicket = createUserTicket(userId, ticketNumber, lottery.getTicketPrice());
        userTicketRepository.save(userTicket);
        return new LotteryPurchaseResponseDto(userTicket.getTicketId());
    }

    private void validateLotteryAvailable(final Lottery lottery, final String ticketNumber) {
        if (lottery.getTicketAmount() <= 0) {
            throw new NotFoundException(String.format(LOTTERY_SOLD_OUT_MESSAGE_FORMAT, ticketNumber));
        }
    }

    private void createUser(final String userId) {
        final Users newUser = new Users();
        newUser.setUserId(userId);
        usersRepository.save(newUser);
    }

    private void decrementTicketAmount(final Lottery lottery) {
        lottery.setTicketAmount(lottery.getTicketAmount() - 1);
        lotteryRepository.save(lottery);
    }

    private UserTicket createUserTicket(final String userId, final String ticketNumber, final BigDecimal ticketPrice) {
        final UserTicket userTicket = new UserTicket();
        userTicket.setTicketNumber(ticketNumber);
        userTicket.setUserId(userId);
        userTicket.setPricePaid(ticketPrice);
        userTicket.setPurchaseDate(LocalDateTime.now());
        userTicket.setIsSoldBackFlag(LOTTERY_NOT_SOLD_BACK_FLAG);
        userTicket.setLastUpdated(LocalDateTime.now());
        return userTicket;
    }

}
