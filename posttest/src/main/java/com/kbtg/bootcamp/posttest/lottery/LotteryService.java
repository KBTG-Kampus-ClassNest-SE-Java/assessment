package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.StatusInternalServerErrorException;
import com.kbtg.bootcamp.posttest.payload.LotteryRequestDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotteryService {

	private final LotteryRepository lotteryRepository;

	public LotteryService(LotteryRepository lotteryRepository) {
		this.lotteryRepository = lotteryRepository;
	}

	@Transactional
	public String createLottery(LotteryRequestDto requestDto) {

		Optional<Lottery> optionalTicket = lotteryRepository.findByTicket(requestDto.getTicket());
		if (optionalTicket.isPresent()) {
			throw new StatusInternalServerErrorException("This Ticket ID is already in the store");

		}
		Lottery lottery = new Lottery();
		lottery.setTicket(requestDto.getTicket());
		lottery.setPrice(requestDto.getPrice());
		lottery.setAmount(requestDto.getAmount());
		lotteryRepository.save(lottery);
		return lottery.getTicket();

	}
	public List<String> getLotteries() {
		return lotteryRepository.findAll().stream().filter(lottery -> lottery.getAmount() > 0).map(Lottery::getTicket).toList();
	}
}

