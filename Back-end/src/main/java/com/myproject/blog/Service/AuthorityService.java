package com.myproject.blog.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.blog.Model.Authority;
import com.myproject.blog.Repository.AuthorityRepository;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    public void save(Authority authority) {
        authorityRepository.save(authority);
    }

    public Optional<Authority> findById(Integer id) {
        // TODO Auto-generated method stub
        return authorityRepository.findById(id);
    }

    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }
    
}
