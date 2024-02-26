package com.kbtg.bootcamp.posttest.security;

import com.kbtg.bootcamp.posttest.user.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer id;
    private String role;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRole;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
