package com.kbtg.bootcamp.posttest.user.service;

import com.kbtg.bootcamp.posttest.entity.User;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.response.GetAllTicketResponse;

public interface UserService {

  User validateUser(Long userId) throws NotFoundException;

  Long buyTicketByUserId(Long userId, String ticket) throws Exception;

  String deleteUserTicket(Long userId, String ticket) throws Exception;

  GetAllTicketResponse getAllTicketsByUserId(Long userId) throws Exception;
}
