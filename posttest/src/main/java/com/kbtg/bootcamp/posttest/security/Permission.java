package com.kbtg.bootcamp.posttest.security;

import com.kbtg.bootcamp.posttest.user.UserPermission;
import com.kbtg.bootcamp.posttest.user.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="permission_id")
    private Integer id;
    private String permission;

    @OneToMany(mappedBy = "permission")
    private List<UserPermission> userPermissions;

    public Permission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
