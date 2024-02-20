package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.admin.AdminRequest;
import com.kbtg.bootcamp.posttest.profile.Profile;
import com.kbtg.bootcamp.posttest.profile.ProfileRepository;
import com.kbtg.bootcamp.posttest.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public LotteryResponse createLottery(AdminRequest request) {
        Objects.requireNonNull(request);
        Lottery newLottery = new Lottery(request.ticket(), request.price(), request.amount());

        if (isExistedByTicket(newLottery.getTicket())) {
            throw new DuplicateTickerException("Duplicate entity");
        } else {
            // new lottery
            lotteryRepository.save(newLottery);
        }
        return new LotteryResponse(newLottery.getTicket());
    }

    private boolean isExistedByTicket(String ticket) {
        return lotteryRepository.existsByTicket(ticket);
    }

    public List<Lottery> getAllLotteries() {
        return lotteryRepository.findAll(); // null
    }

    public ResponseEntity<? extends Object> buyLotteries(UserRequest request) {
        Objects.requireNonNull(request);

        if (isExistedByTicket(request.ticketId())) { // there is a ticketNumber in the Lottery table

            // find user that correspond with requestID
            Profile profile = profileRepository.findById(request.userId()).orElse(null);
            if (profile == null) {
                return ResponseEntity.notFound().build(); // User not found
            }
            Lottery lottery = lotteryRepository.findById(Long.valueOf(request.ticketId())).get();
            if (lottery == null) {
                // Return a proper error response if the lottery ticket is not found
                throw new NotExistLotteryException("Lottery ticket not found");
            }
            lottery.setProfile(profile);
            return ResponseEntity.ok().body(String.valueOf(profile.getId()));
        } else {
            throw new NotExistLotteryException("Wrong Lottery ticketNumber");
        }

    }
}
