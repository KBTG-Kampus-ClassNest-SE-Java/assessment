package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.admin.AdminRequest;
import com.kbtg.bootcamp.posttest.exception.DuplicateLotteryException;
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
                throw new DuplicateLotteryException("Duplicate Lottery!");
            }
            lotteryRepository.save(newLottery);
            LotteryResponse lotteryResponse =  new LotteryResponse(newLottery.getTicket());
            return ResponseEntity.ok(lotteryResponse);
        } catch (DuplicateLotteryException e) {
            return ResponseEntity.badRequest().body("Duplicate Lottery!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public List<Lottery> getAllLotteries() {
        return lotteryRepository.findAll();
    }

    public List<Lottery> getLotteriesWithNullProfile() {
        return getAllLotteries().stream()
                .filter(lottery -> lottery.getProfile() == null).collect(Collectors.toList());
    }
    @Transactional
    public ResponseEntity<?> buyLotteries(UserRequest request) {
        Objects.requireNonNull(request);
        responseBody = new HashMap<>();

        try {
            validateInputInformation(request.userId(), request.ticketId());
            // userId && lotteryNumber exists and User don't have this lottery
            if  (!isLotteryTicketBelongsToUser(request.userId(), request.ticketId())) {
                List<Lottery> lotteriesWithNullProfile = getLotteriesWithNullProfile();
                Optional<Lottery> lotteryThatWeWantToBuy = lotteriesWithNullProfile.stream()
                        .filter(lottery -> lottery.getTicket().equals(request.ticketId())).findFirst();

                if (lotteryThatWeWantToBuy.isPresent()) {

                    // Create or retrieve the profile for the given userId
                    Profile profile = profileRepository.findByUserIdEquals(request.userId());
                    if (profile == null) {
                        // If the profile doesn't exist, create a new one
                        profile = new Profile();
                        profile.setUserId(request.userId());
                        profile = profileRepository.save(profile); // Save the profile
                    }
                    Lottery lotteryToBuy = lotteryThatWeWantToBuy.get();
                    lotteryToBuy.setProfile(profile);
                    lotteryRepository.save(lotteryToBuy);
                    responseBody.put("id", request.userId());
                }
            } else {
                throw new DuplicateLotteryException("You already have one");
            }
            return ResponseEntity.ok().body(responseBody);

        } catch (DuplicateLotteryException e) {
            return ResponseEntity.badRequest().body("You already have one");
        } catch (NotExistLotteryException e) {
            return ResponseEntity.badRequest().body("Lottery does not exist");
        } catch (NotExistUserIdException e) {
            return ResponseEntity.badRequest().body("UserId does not exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
            return ResponseEntity.badRequest().body("Wrong user id");
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
        validateInputInformation(userId, lotteryNumber);

        // Check if the lottery belongs to the user
        boolean lotteryBelongsToUser = isLotteryTicketBelongsToUser(userId, lotteryNumber);
        if (!lotteryBelongsToUser) {
            throw new LotteryNotBelongToUserException("Lottery does not belong to user");
        }
    }

    public void validateInputInformation(String userId, String lotteryNumber) {
        boolean lotteryExists = isLotteryExistsByTicketNumber(lotteryNumber);
        if (!lotteryExists) {
            throw new NotExistLotteryException("Lottery does not exist");
        }

        // Check if the user exists
        boolean userExists = isUserExistsByUserId(userId);
        if (!userExists) {
            throw new NotExistUserIdException("User does not exist");
        }
    }


    @Transactional
    public ResponseEntity<?> sellLotteryByUsingUserIdAndLotteryTicket(String requestedUserID,
                                                                      String requestedLotteryId) {
        Objects.requireNonNull(requestedLotteryId);
        Objects.requireNonNull(requestedLotteryId);
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
