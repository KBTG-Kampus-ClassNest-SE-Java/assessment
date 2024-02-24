package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.dao.LotteryRepository;
import com.kbtg.bootcamp.posttest.dto.ProductResponseDTO;
import com.kbtg.bootcamp.posttest.entity.LotteryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private LotteryRepository lotteryRepository;


    public ProductResponseDTO getAllProduct() throws Exception {
        List<String> ticket = this.lotteryRepository.findAllWithOutOwner()
                .stream().map(LotteryEntity::getTicketNumber)
                .toList();
        return new ProductResponseDTO(ticket,null,null);
    }

}
