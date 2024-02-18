package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lottery")
@Getter
@Setter
@NoArgsConstructor
public class Lottery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "ticket", unique = true, nullable = false, length = 6)
  private String ticket;

  @Column(name = "price", nullable = false)
  private Integer price;

  @Column(name = "amount", nullable = false)
  private Integer amount;

}
