package com.kbtg.bootcamp.posttest.admin.controller;

import com.kbtg.bootcamp.posttest.admin.service.AdminService;
import com.kbtg.bootcamp.posttest.app.helper.ResponseHandler;
import com.kbtg.bootcamp.posttest.admin.request.CreateLotteryRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/lotteries")
public class AdminController {

  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Object> createLottery(@Valid @RequestBody CreateLotteryRequest createLotteryRequest) {
      return ResponseHandler.generateResponse(
          "Create lottery success.",
          HttpStatus.CREATED,
          adminService.createLottery(createLotteryRequest));
  }


}
