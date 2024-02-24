package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.admin.AdminRequest;
import com.kbtg.bootcamp.posttest.exception.DuplicateTickerException;
import com.kbtg.bootcamp.posttest.exception.LotteryNotBelongToUserException;
import com.kbtg.bootcamp.posttest.exception.NotExistLotteryException;
import com.kbtg.bootcamp.posttest.exception.NotExistUserIdException;
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

    private Map<String, String> responseBody;

    public LotteryService(LotteryRepository lotteryRepository, ProfileRepository profileRepository) {
        this.lotteryRepository = lotteryRepository;
        this.profileRepository =  profileRepository;
    }

    @Transactional
    public ResponseEntity<?> createLottery(AdminRequest request) {
        Objects.requireNonNull(request);
        Lottery newLottery = new Lottery(request.ticket(), request.price(), request.amount());
        try {
            if (isLotteryExistsByTicketNumber(request.ticket())) {
                throw new DuplicateTickerException("Duplicate Lottery!");
            }
            lotteryRepository.save(newLottery);
            LotteryResponse lotteryResponse =  new LotteryResponse(newLottery.getTicket());
            return ResponseEntity.ok(lotteryResponse);
        } catch (DuplicateTickerException e) {
            return ResponseEntity.badRequest().body("Lottery does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public List<Lottery> getAllLotteries() {
        return lotteryRepository.findAll();
    }

    @Transactional
    public ResponseEntity<?> buyLotteries(UserRequest request) {
        Objects.requireNonNull(request);
        responseBody = new HashMap<>();

        try {
            validateLotteryOwnership(request.userId(), request.ticketId());
            Lottery result = lotteryRepository.findByTicket(request.ticketId());

            result.setProfile(profileRepository.findByUserIdEquals(request.userId()));
            lotteryRepository.save(result);

            responseBody.put("id", request.userId());
            return ResponseEntity.ok().body(responseBody);

        } catch (Exception e) {
            return handleException(e);
        }
    }


    public ResponseEntity<?> getUserLotteryDetail(String requestedUserId) {

        try {
            int counter = 0;
            double totalPrice = 0.0;
            responseBody = new LinkedHashMap<>();
            if (isUserExistsByUserId(requestedUserId)) {
                List<Lottery> collect = getAllLotteriesByUserId(requestedUserId);

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
                responseBody.put("count", Integer.toString(counter));
                responseBody.put("totalPrice", Double.toString(totalPrice));
                return ResponseEntity.ok(responseBody);
            } else {
                throw new NotExistUserIdException("Wrong user id");
            }
        } catch (NotExistUserIdException e) {
            return ResponseEntity.badRequest().body("Lottery does not exist");
        }
    }

    public List<Lottery> getAllLotteriesByUserId(String requestedUserId) {
        return getAllLotteries().stream()
                .filter(lottery -> {
                    Profile profile = lottery.getProfile();
                    return profile != null && profile.getUserId() != null && profile.getUserId().equals(requestedUserId);
                })
                .collect(Collectors.toList());
    }

    public boolean isUserExistsByUserId(String requestedUserId) {
        return profileRepository.findAll().stream()
                .anyMatch(profile -> profile.getUserId().equals(requestedUserId));
    }

    public boolean isLotteryTicketBelongsToUser(String userId, String lotteryNumber) {
        List<Lottery> userLotteries = getAllLotteriesByUserId(userId);
        return userLotteries.stream()
                .anyMatch(lottery -> lottery.getTicket().equals(lotteryNumber));
    }


    public boolean isLotteryExistsByTicketNumber(String ticket) {
        return lotteryRepository.findAll().stream()
                .anyMatch(lottery -> lottery.getTicket().equals(ticket));
    }

    public void validateLotteryOwnership(String userId, String lotteryNumber) {
        // Check if the lottery exists
        boolean lotteryExists = isLotteryExistsByTicketNumber(lotteryNumber);
        if (!lotteryExists) {
            throw new NotExistLotteryException("Lottery does not exist");
        }

        // Check if the user exists
        boolean userExists = isUserExistsByUserId(userId);
        if (!userExists) {
            throw new NotExistUserIdException("User does not exist");
        }

        // Check if the lottery belongs to the user
        boolean lotteryBelongsToUser = isLotteryTicketBelongsToUser(userId, lotteryNumber);
        if (!lotteryBelongsToUser) {
            throw new LotteryNotBelongToUserException("Lottery does not belong to user");
        }
    }


    @Transactional
    public ResponseEntity<?> sellLotteryByUsingUserIdAndLotteryTicket(String requestedUserID,
                                                                      String requestedLotteryId) {
        try {
            validateLotteryOwnership(requestedUserID, requestedLotteryId);
            List<Lottery> allLotteriesByUserId = new ArrayList<>();
            responseBody = new HashMap<>();

            // find the lottery that has the profile equal to requestedUserID
            allLotteriesByUserId = getAllLotteriesByUserId(requestedUserID);

            // delete profile and amount from lottery that match Lottery number
            allLotteriesByUserId.stream() // Stream<Lottery>
                    .filter(lottery -> lottery.getTicket().equals(requestedLotteryId))
                    .forEach(lottery -> {
                        lottery.setProfile(null);
                        lottery.setAmount(0L);
                        lotteryRepository.save(lottery);
                    });

            responseBody.put("id",requestedUserID);

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    public ResponseEntity<?> handleException(Exception e) {
        return switch (e.getClass().getSimpleName()) {
            case "NotExistLotteryException" ->
                    ResponseEntity.badRequest().body("Lottery does not exist");
            case "NotExistUserIdException" ->
                    ResponseEntity.badRequest().body("User does not exist");
            case "LotteryNotBelongToUserException" ->
                    ResponseEntity.badRequest().body("Lottery does not belong to user.");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        };
    }




}
