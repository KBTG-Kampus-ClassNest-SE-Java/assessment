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
import com.kbtg.bootcamp.lottery.response.LotteryUserResponseDto;
import com.kbtg.bootcamp.lottery.service.LotteryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.kbtg.bootcamp.lottery.lib.Constant.*;

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
    public LotteryResponseDto createLottery(LotteryRequestDto lotteryDTO) throws Exception {
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
        List<String> ticketNumbers = lotteryRepository
                .findAll()
                .stream()
                .map(Lottery::getTicketNumber)
                .toList();
        return new LotteryResponseDto(ticketNumbers);
    }

    @Override
    @Transactional
    public LotteryResponseDto deleteLottery(String userId, String ticketId) throws Exception {
        Users user = new Users();
        user.setUserId(userId);
        boolean isExist = userTicketRepository.existsByTicketIdAndUserId(user, ticketId);
        if (!isExist) {
            throw new NotFoundException(TICKET_NOT_FOUND_OR_USER_ID);
        }
        Lottery ticket =  userTicketRepository.findByTicketIdAndUserId(user, Long.valueOf(ticketId));
        userTicketRepository.deleteLotteryTicketById(user, Long.valueOf(ticketId));
        Optional<Lottery> optionalLottery = lotteryRepository.findById(ticket.getTicketNumber());
        if (optionalLottery.isPresent()) {
            Lottery lottery = optionalLottery.get();
            lottery.setTicketAmount(lottery.getTicketAmount() + 1);
            lotteryRepository.save(lottery);
        }
        return new LotteryResponseDto(ticket.getTicketNumber());
    }


    @Override
    @Transactional
    public LotteryUserResponseDto getUserLotteryTickets(String userId) throws Exception {
        Users user = new Users();
        user.setUserId(userId);
        List<UserTicket> userTickets = userTicketRepository.findByUserId(user);
        if (userTickets.isEmpty()) {
            throw new NotFoundException(String.format(USER_NOT_FOUND, userId));
        }
        List<String> ticketNumbers = userTickets.stream()
                .map(UserTicket::getLotteryNumber)
                .toList();
        BigDecimal totalPrice = userTicketRepository.getTotalPricePaidByUserId(user);
        Long totalTicket = userTicketRepository.getTicketCountByUserId(user);
        return new LotteryUserResponseDto(ticketNumbers, totalPrice, totalTicket);
    }

    @Override
    @Transactional
    public LotteryPurchaseResponseDto purchaseLotteryTicket(final String userId,final String lotteryNumber) throws Exception {
        Optional<Lottery> optionalLottery = lotteryRepository.findById(lotteryNumber);

        if (optionalLottery.isEmpty()) {
                throw new NotFoundException(String.format(LOTTERY_NOT_FOUND, lotteryNumber));
        }

        Lottery lottery = optionalLottery.get();
        validateLotteryAvailable(lottery, lotteryNumber);


        Optional<Users> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isEmpty()){
            createUser(userId);
        }

        decrementTicketAmount(lottery);
        final UserTicket userTicket = createUserTicket(userId, lotteryNumber, lottery.getTicketPrice());
        userTicketRepository.save(userTicket);
        return new LotteryPurchaseResponseDto(userTicket.getTicketId());
    }

    private void validateLotteryAvailable(Lottery lottery, String ticketNumber) {
        if (lottery.getTicketAmount() <= 0) {
            throw new NotFoundException(String.format(LOTTERY_SOLD_OUT_MESSAGE_FORMAT, ticketNumber));
        }
    }

    private void createUser(String userId) {
        final Users newUser = new Users();
        newUser.setUserId(userId);
        usersRepository.save(newUser);
    }

    private void decrementTicketAmount(Lottery lottery) {
        lottery.setTicketAmount(lottery.getTicketAmount() - 1);
        lotteryRepository.save(lottery);
    }

    private UserTicket createUserTicket(String userId, String ticketNumber, BigDecimal ticketPrice) {
        UserTicket userTicket = new UserTicket();
        userTicket.setLotteryNumber(ticketNumber);
        userTicket.setUserId(userId);
        userTicket.setPricePaid(ticketPrice);
        userTicket.setPurchaseDate(LocalDateTime.now());
        userTicket.setIsSoldBackFlag(LOTTERY_NOT_SOLD_BACK_FLAG);
        userTicket.setLastUpdated(LocalDateTime.now());
        return userTicket;
    }

}
