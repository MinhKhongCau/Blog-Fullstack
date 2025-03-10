package com.myproject.blog.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myproject.blog.Model.Account;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepsitory extends JpaRepository<Account, Integer> {
	Optional<Account> findOneByEmailIgnoreCase(String email);

	@Query(value = "SELECT * FROM account WHERE TOKEN = :token", nativeQuery = true)
	Optional<Account> findByToken(@Param("token") String token);

	@Modifying
	@Transactional
	@Query(value = "EXEC SP_UPDATE_ACCOUNT :id, :age, :birthdate, :email, :firstname, :gender, " +
			":lastname, :password, :photo, :reset_password_expiry, :reset_password_token, :role, :token", nativeQuery = true)
	void updateAccount(
			@Param("id") Integer id,
			@Param("age") Integer age,
			@Param("birthdate") Date birthdate,
			@Param("email") String email,
			@Param("firstname") String firstname,
			@Param("gender") Boolean gender,
			@Param("lastname") String lastname,
			@Param("password") String password,
			@Param("photo") String photo,
			@Param("reset_password_expiry") LocalDateTime resetPasswordExpiry,
			@Param("reset_password_token") String resetPasswordToken,
			@Param("role") Integer role,
			@Param("token") String token
	);
}
