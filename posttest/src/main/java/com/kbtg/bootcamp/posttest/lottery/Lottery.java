package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.core.entity.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Table(name = "lottery")
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Lottery extends AuditEntity {
    @Id
    @GeneratedValue(generator = "lottery_generator")
    @SequenceGenerator(name = "lottery_generator", sequenceName = "lottery_sequence", initialValue = 1)
    private Long id;

    @Length(min = 6, max = 6, message = "Ticket must contain 6 characters")
    private String ticket;

    @DecimalMin(value = "0.00", message = "Price has to be non negative number")
    private BigDecimal price;

    @Min(value = 1, message = "Amount must be at least 1")
    private Integer amount;
}
