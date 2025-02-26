package com.myproject.blog.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myproject.blog.Model.Account;

@Repository
public interface AccountRepsitory extends JpaRepository<Account,Integer> {
    Optional<Account> findOneByEmailIgnoreCase(String email);

    @Query(value = "SELECT * FROM account WHERE TOKEN = :token", nativeQuery = true)
    Optional<Account> findByToken(@Param("token") String token);

}
