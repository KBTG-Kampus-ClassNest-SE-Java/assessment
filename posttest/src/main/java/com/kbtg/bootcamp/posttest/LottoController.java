package com.kbtg.bootcamp.posttest;



import org.springframework.web.bind.annotation.*;

import java.util.*;


class Lotto {
    private int id;
    private String ticket;

    private int price= 80;

    public Lotto(int id, String ticket, int price) {
        this.id = id;
        this.ticket = ticket;
        this.price =price;
    }

    public int getId() {
        return id;
    }

    public String getTicket() {
        return ticket;
    }

    public int getPrice() {
        return price;
    }
}



@RestController
public  class LottoController {

    List<Lotto> lottos = new ArrayList<>(List.of(
            new Lotto(1, "000001", 80),
            new Lotto(2, "000002",80),
            new Lotto(3, "123456",80)
    ));

    @GetMapping("/lotteries")
    private List<Lotto> getLotto() {
        return lottos;
    }

   /*@GetMapping("/lotteries")
    public Map<String, Object> getLotteries() {
        List<String> tickets = new ArrayList<>();
        for (Lotto lotto : lottos) {
            tickets.add(lotto.getTicket());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("tickets", tickets);
        return response;
    }*/



   /* @PostMapping("/lotteries")
    public  void createLotto(@RequestBody Lotto lottoDetail){

        lottos.add(lottoDetail);
    }*/

    @PostMapping("/lotteries")
    public void createLotto(@RequestBody Lotto lottoDetail) {
        int maxId = lottos.stream()
                .mapToInt(Lotto::getId)
                .max()
                .orElse(0);

        Lotto newLotto = new Lotto(maxId + 1, lottoDetail.getTicket(), lottoDetail.getPrice());
        lottos.add(newLotto);
    }
}

