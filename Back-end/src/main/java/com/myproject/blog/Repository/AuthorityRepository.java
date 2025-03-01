package com.myproject.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.blog.Model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    
}
