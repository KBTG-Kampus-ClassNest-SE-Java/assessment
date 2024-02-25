package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("")
public class PublicController {

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/lotteries")
    public Map<String, List<String>> getLottery(){
        return this.publicService.getLottery();
    }


    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegisterDto requestDto) {
        return this.publicService.registerUser(requestDto);
    }
}
