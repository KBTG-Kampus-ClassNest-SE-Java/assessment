package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.admin.AdminRequest;
import com.kbtg.bootcamp.posttest.exception.DuplicateTickerException;
import com.kbtg.bootcamp.posttest.exception.NotExistLotteryException;
import com.kbtg.bootcamp.posttest.exception.NotExistUserIdException;
import com.kbtg.bootcamp.posttest.profile.Profile;
import com.kbtg.bootcamp.posttest.profile.ProfileRepository;
import com.kbtg.bootcamp.posttest.user.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Map<String, String> responseBody;


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
        responseBody = new HashMap<>();
        // check there is a lottery that request
        if (isLotteryExistsByTicketNumber(request.ticketId())) {
            Lottery byTicket = lotteryRepository.findByTicket(request.ticketId());

            byTicket.setProfile(profileRepository.findByUserIdEquals(request.userId()));
            lotteryRepository.save(byTicket);

            responseBody.put("id", request.userId());
            return ResponseEntity.ok().body(responseBody);
        } else {
            throw new NotExistLotteryException("Wrong ticket number");
        }
    }


    public Map<String, String> getAllLotteriesByUserId(String requestedUserId) {
        Integer counter = 0;
        Double totalPrice = 0.0;
        responseBody = new LinkedHashMap<>();
        if (isUserExistsByUserId(requestedUserId)) {
            List<Lottery> collect = getAllLotteries().stream()
                    .filter(lottery -> {
                        Profile profile = lottery.getProfile();
                        return profile != null && profile.getUserId() != null && profile.getUserId().equals(requestedUserId);
                    })
                    .collect(Collectors.toList());

            List<String> ticketList = collect.stream()
                    .map(Lottery::getTicket)
                    .collect(Collectors.toList());

                    counter = collect.size();

            totalPrice = collect.stream()
                    .mapToDouble(
                            lottery -> lottery.getPrice() * lottery.getAmount()
                    )
                    .sum();
            responseBody.put("tickets", String.join(",",ticketList));
            responseBody.put("count", counter.toString());
            responseBody.put("totalPrice", totalPrice.toString());
            return responseBody;
        } else {
            throw new NotExistUserIdException("Wrong user id");
        }
    }

    public boolean isUserExistsByUserId(String requestedUserId) {
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
