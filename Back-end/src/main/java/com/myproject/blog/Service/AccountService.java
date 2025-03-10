package com.myproject.blog.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.blog.Model.Account;
import com.myproject.blog.Model.Authority;
import com.myproject.blog.Repository.AccountRepsitory;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepsitory accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<Account> getById(Integer id) {
		return accountRepository.findById(id);
	}

	public List<Account> getAll() {
		return accountRepository.findAll();
	}

	public String updateAccount(Account account) {
		accountRepository.updateAccount(
				account.getId(),
				account.getAge(),
				account.getBirthDate(),
				account.getEmail(),
				account.getFirstName(),
				account.getGender(),
				account.getLastName(),
				account.getPassword(),
				account.getPhoto(),
				account.getResetPasswordExpiry(),
				account.getResetPasswordToken(),
				account.getRole(),
				account.getToken()
		);
		return "Account updated successfully.";
	}

	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		if (account.getPhoto() == null) {
			account.setPhoto("/images/person_img.png");
		}
		System.out.println("*** Saved account: " + account.toString());

		return accountRepository.save(account);
	}

	public void delete(Account account) {
		accountRepository.delete(account);
	}

	public Optional<Account> getByEmail(String email) {
		return accountRepository.findOneByEmailIgnoreCase(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("***Load email: " + email);
		Optional<Account> optionalAccount = getByEmail(email);
		if (!optionalAccount.isPresent()) {
			System.out.println("***This user exited: " + optionalAccount.get().getEmail());
			throw new UsernameNotFoundException(email);
		}

		Account account = optionalAccount.get();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole().toString()));

		for (Authority auth : account.getAuthorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(auth.getName()));
		}

		User user = new User(account.getEmail(), account.getPassword(), grantedAuthorities);
		System.out.println("***User is save in authorities: " + user);

		return user;
	}

	public Optional<Account> getByToken(String token) {
		return accountRepository.findByToken(token);
	}

}
