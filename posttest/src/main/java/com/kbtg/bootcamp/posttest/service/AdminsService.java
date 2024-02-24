package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dao.LotteryRepository;
import com.kbtg.bootcamp.posttest.dto.AdminRequestDto;
import com.kbtg.bootcamp.posttest.dto.AdminResponseDto;
import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import com.kbtg.bootcamp.posttest.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class AdminsService {

    @Autowired
    private LotteryRepository  lotteryRepository;

    public void validateAdminReq(AdminRequestDto requestDto) throws ValidationException {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(requestDto.getPrice()))
            validationErrors.put("price", "must not be null");
        if (ObjectUtils.isEmpty(requestDto.getAmount()))
            validationErrors.put("amount", "must not be null");
        if (ObjectUtils.isEmpty(requestDto.getTicket())) {
            validationErrors.put("ticket", "must not be null");
        } else {
            if (!Pattern.compile("\\d{6}").matcher(requestDto.getTicket()).matches())
                validationErrors.put("ticket", "must be 6 digits");
        }
        if (!validationErrors.isEmpty())
            throw new ValidationException(validationErrors);
    }

    public AdminResponseDto createLottery(AdminRequestDto requestDto) {
        LotteryEntity lottery = saveLottery(new LotteryEntity(
                requestDto.getTicket(),
                BigDecimal.valueOf(requestDto.getPrice().longValue()),
                requestDto.getAmount()
        ));
        AdminResponseDto response = new AdminResponseDto();
        response.setTicket(lottery.getTicketNumber());
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public LotteryEntity saveLottery(LotteryEntity LotteryData){
        return this.lotteryRepository.saveAndFlush(LotteryData);
    }
}
