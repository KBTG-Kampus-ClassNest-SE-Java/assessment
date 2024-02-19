package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lottery")
public class Lottery {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "ticket", unique = true, nullable = false, length = 6)
  private String ticket;

  private int amount;

  private int price;



}
