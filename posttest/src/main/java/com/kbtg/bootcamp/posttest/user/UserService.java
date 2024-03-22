package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.InternalServerStatusErrorException;
import com.kbtg.bootcamp.posttest.lottery.LotteryModel;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.payload.LotteryDetailDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository ;
    private final LotteryRepository lotteryRepository;

    public UserService(UserRepository userRepository, LotteryRepository lotteryRepository) {
        this.userRepository = userRepository;
        this.lotteryRepository = lotteryRepository;
    }
    @Transactional
    public String createUserAndLottery(String userId, String ticketId) {
        Optional<LotteryModel> optionalLottery = lotteryRepository.findByTicket(ticketId);
        if (optionalLottery.isEmpty() || optionalLottery.get().getAmount() == 0) {
            throw new InternalServerStatusErrorException(
                    "Ticket ID : " + ticketId + " is not available.");
        }
        LotteryModel selectedLottery = optionalLottery.get();
        UserModel userLottery = new UserModel();
        userLottery.setUserId(userId);
        userLottery.setTicketId(selectedLottery);

        UserModel savedUserLottery = userRepository.save(userLottery);
        int updatedAmount = selectedLottery.getAmount() - 1;
        selectedLottery.setAmount(updatedAmount);
        lotteryRepository.save(selectedLottery);
        return savedUserLottery.getId().toString();
    }
    public LotteryDetailDto getUserDetail(String userId) {
        List<UserModel> lotteryListByUserId = userRepository.findByUserId(userId);
        if (lotteryListByUserId.isEmpty()) {
            throw new BadRequestException("User ID : " + userId + " is not found.");
        }
        List<String> lotteryList = lotteryListByUserId.stream().map(UserModel::getTicketId).map(LotteryModel::getTicket).toList();
        int count = lotteryListByUserId.size();
        //int cost = lotteryListByUserId.stream().map(UserModel::getTicketId).mapToInt(LotteryModel::getPrice).sum();
        int cost = 0;
        for (UserModel userModel : lotteryListByUserId) {
            LotteryModel ticketId = userModel.getTicketId();
            int price = (int) ticketId.getPrice();
            cost += price;
        }
        return new LotteryDetailDto(lotteryList, count, cost);
    }
    @Transactional
    public String sellLotteryByUserIdAndTicketId(String userId, String ticketId) {
        List<UserModel> lotteryListByUserId = userRepository.findByUserId(userId);

        if (lotteryListByUserId.isEmpty()) {
            throw new BadRequestException("User ID : " + userId + " is not bought Ticket ID : " + ticketId);
        }
        Optional<UserModel> optionalLottery = lotteryListByUserId.stream()
                .filter(tempLottery -> Objects.equals(tempLottery
                        .getTicketId()
                        .getTicket(), ticketId))
                .findAny();
        UserModel userLottery = optionalLottery.stream().findFirst().orElse(null);
        //User userLottery = optionalLottery.get();

        if (userLottery != null){
            userRepository.delete(userLottery);
        }else {

        }


        return ticketId;
    }
}
