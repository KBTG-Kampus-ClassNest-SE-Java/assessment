package com.kbtg.bootcamp.posttest.user;

import jakarta.persistence.criteria.CriteriaBuilder;

public record UserRequest(Integer userId, String ticketId) {
}
