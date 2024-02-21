package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.admin.AdminRequest;
import com.kbtg.bootcamp.posttest.profile.Profile;
import com.kbtg.bootcamp.posttest.profile.ProfileRepository;
import com.kbtg.bootcamp.posttest.user.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LotteryService {
    @Autowired
    private final LotteryRepository lotteryRepository;

    @Autowired
    private final ProfileRepository profileRepository;


    public LotteryService(LotteryRepository lotteryRepository, ProfileRepository profileRepository) {
        this.lotteryRepository = lotteryRepository;
        this.profileRepository =  profileRepository;
    }


    public List<Profile> getAllUserProfile() {
        return
        profileRepository.findAll();
    }

    public Optional<Lottery> getLottery(Long id) {
        return lotteryRepository.findById(id);
    }

    @Transactional
    public LotteryResponse createLottery(AdminRequest request) {
        Objects.requireNonNull(request);
        Lottery newLottery = new Lottery(request.ticket(), request.price(), request.amount());

        if (isLotteryExistsByTicketNumber(newLottery.getTicket())) {
            throw new DuplicateTickerException("Duplicate entity");
        } else {
            // new lottery
            lotteryRepository.save(newLottery);
        }
        return new LotteryResponse(newLottery.getTicket());
    }





    public List<Lottery> getAllLotteries() {
        return lotteryRepository.findAll(); // null
    }

    @Transactional
    public ResponseEntity<?> buyLotteries(UserRequest request) {
        Objects.requireNonNull(request);
        // check there is a lottery that request
        if (isLotteryExistsByTicketNumber(request.ticketId())) {
            Lottery byTicket = lotteryRepository.findByTicket(request.ticketId());

            byTicket.setProfile(profileRepository.findByUserIdEquals(request.userId()));
            lotteryRepository.save(byTicket);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("id", request.userId());
            return ResponseEntity.ok().body(responseBody);
        } else {
            throw new NotExistLotteryException("Wrong ticket number");
        }
    }

    @Transactional
    public Map<String, String> getAllLotteriesByUserId(Integer requestedUserId) {
        Integer count = 0;
        Double totalPrice = 0.0;
        Map<String, String > responseBody = new HashMap<>();
        if (isUserExistsByUserId(requestedUserId)) {
            List<Lottery> collect = getAllLotteries().stream()
                    .filter(lottery -> lottery.getProfile().getUserId().equals(requestedUserId))
                    .collect(Collectors.toList());

            List<String> ticketList = collect.stream()
                    .map(Lottery::getTicket)
                    .collect(Collectors.toList());

            count = collect.size();

            totalPrice = collect.stream()
                    .mapToDouble(
                            lottery -> lottery.getPrice() * lottery.getAmount()
                    )
                    .sum();
            responseBody.put("tickets", String.join(",",ticketList));
            responseBody.put("count", count.toString());
            responseBody.put("totalPrice", totalPrice.toString());
        } else {
            throw new NotExistUserIdException("Wrong user id");
        }
        return responseBody;
    }

    private boolean isUserExistsByUserId(Integer requestedUserId) {
        return
        profileRepository.findAll().stream()
                .filter(profile -> profile.getUserId().equals(requestedUserId))
                .findFirst().isPresent();
    }

    public boolean isLotteryExistsByTicketNumber(String ticket) {
        return
        lotteryRepository.findAll().stream()
                .filter(lottery -> lottery.getTicket().equals(ticket))
                .findFirst().isPresent();
    }
}
