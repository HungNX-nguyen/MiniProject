package com.company.miniproject.repository;

import com.company.miniproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query("SELECT a FROM Account a" +
            " LEFT JOIN FETCH a.accountRoles ar" +
            " LEFT JOIN FETCH ar.role" +
            " WHERE a.username = :username")
    Optional<Account> findByUsernameWithRoles(String username);
}
