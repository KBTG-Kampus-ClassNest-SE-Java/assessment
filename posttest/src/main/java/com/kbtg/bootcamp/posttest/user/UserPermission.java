package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.security.Permission;
import jakarta.persistence.*;

@Entity
@Table(name="user_permission")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_permission_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="permission_id")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

    public UserPermission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
