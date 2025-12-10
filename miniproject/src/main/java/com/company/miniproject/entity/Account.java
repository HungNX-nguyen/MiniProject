package com.company.miniproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountStatus status =  AccountStatus.ACTIVE;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AccountRole> accountRoles = new HashSet<>();

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private Employee employee;

    public enum AccountStatus {
        ACTIVE,BLOCKED
    }

}

