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

    public boolean isExistedByTicket(String ticket) {
        // return false if ticket does not exist -> new
        Optional<Lottery> result = lotteryRepository.findAll().stream()
                .filter(lottery -> lottery.getTicket().equals(ticket))
                .findFirst();
        if (result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public List<Lottery> getAllLotteries() {
        return lotteryRepository.findAll(); // null
    }


    public ResponseEntity<?> buyLotteries(UserRequest request) {
        Objects.requireNonNull(request);
        return ResponseEntity.ok().body(request.userId());
    }
}
