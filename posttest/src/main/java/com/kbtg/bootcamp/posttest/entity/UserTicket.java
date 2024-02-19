package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "user_ticket")
public class UserTicket {

  @Id
  @GeneratedValue
  private Long id;


  private Integer userId;

  @ManyToOne
  @JoinColumn(name = "lottery_id", referencedColumnName = "id")
  private Lottery lottery;




}
