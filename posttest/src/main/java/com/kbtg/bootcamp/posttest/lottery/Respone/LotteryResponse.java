package com.kbtg.bootcamp.posttest.lottery.Respone;

import java.util.List;

public record LotteryResponse(
	List<String> tickets
) {
}