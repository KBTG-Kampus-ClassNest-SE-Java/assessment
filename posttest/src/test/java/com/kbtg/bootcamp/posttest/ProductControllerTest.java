package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.controller.ProductController;
import com.kbtg.bootcamp.posttest.dto.ProductResponseDTO;
import com.kbtg.bootcamp.posttest.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    MockMvc mockMvc;
    @Mock
    ProductService productService;

    @BeforeEach
    void setUp() {
        ProductController productController = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("View all tickets available.")
    void viewLotteries() throws Exception {
        List<String> ticketNumbers = List.of("112233", "090911");
        ProductResponseDTO productResponse = new ProductResponseDTO(ticketNumbers,null,null);
        when(productService.getAllProduct()).thenReturn(productResponse);

        mockMvc.perform(get("/lotteries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", hasSize(2)))
                .andExpect(jsonPath("$.tickets[0]", is("112233")))
                .andExpect(jsonPath("$.tickets[1]", is("090911")))
                .andExpect(jsonPath("$.totalPrice").doesNotExist());
    }

}
