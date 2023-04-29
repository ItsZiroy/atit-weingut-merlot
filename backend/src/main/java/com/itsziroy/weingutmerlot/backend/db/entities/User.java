package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user")
public class User {
    @Column(name = "create_time")
    private Instant createTime;
    @Column(name = "email")
    private String email;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "password", nullable = false)
    private String password;

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}