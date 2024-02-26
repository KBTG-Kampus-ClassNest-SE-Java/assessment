package com.kbtg.bootcamp.posttest.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbtg.bootcamp.posttest.core.entity.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Integer userId;

    @Column(name = "display_name")
    @JsonProperty("display_name")
    @Length(min = 3, max = 50, message = "Display name length should be 3-50 characters")
    private String displayName;

    public User(String displayName) {
        this.displayName = displayName;
    }
}
