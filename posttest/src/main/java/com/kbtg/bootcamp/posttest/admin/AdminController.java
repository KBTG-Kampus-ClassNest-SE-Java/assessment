package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
}
