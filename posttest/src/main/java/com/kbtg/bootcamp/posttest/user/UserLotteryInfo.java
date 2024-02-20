package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;

import java.util.List;

public class UserLotteryInfo {

    private int count;
    private int totalPrice;
    private List<Lottery> lotteries;

    public UserLotteryInfo(int count, int totalPrice, List<Lottery> lotteries) {
        this.count = count;
        this.totalPrice = totalPrice;
        this.lotteries = lotteries;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Lottery> getLotteries() {
        return lotteries;
    }

    public void setLotteries(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }
}
