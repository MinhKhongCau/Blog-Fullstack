package com.myproject.blog.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    private AccountRepsitory accountRepsitory;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Account> getById(Integer id) {
        return accountRepsitory.findById(id);
    }

    public List<Account> getAll() {
        return accountRepsitory.findAll();
    }

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getPhoto() == null) {
            account.setPhoto("/images/person_img.png");
        }
        System.out.println("*** Saved account: "+account.toString());

        return accountRepsitory.save(account);
    }

    public void delete(Account account) {
        accountRepsitory.delete(account);
    }

    public Optional<Account> getByEmail(String email) {
        return accountRepsitory.findOneByEmailIgnoreCase(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        System.out.println("***Load email: "+email); 
        Optional<Account> optionalAccount = getByEmail(email);
        if (!optionalAccount.isPresent()) {
            System.out.println("***This user exited: "+optionalAccount.get().getEmail());
            throw new UsernameNotFoundException(email);
        }
        
        Account account = optionalAccount.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole().toString()));

        for (Authority auth: account.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(auth.getName()));
        }

        System.out.println("***User is save in authorities: "+account.getEmail());
        return new User(account.getEmail(),account.getPassword(),grantedAuthorities);
    }

    public Optional<Account> getByToken(String token) {
        return accountRepsitory.findByToken(token);
    }

}

