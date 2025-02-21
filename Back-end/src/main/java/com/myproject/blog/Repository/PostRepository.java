package com.myproject.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.blog.Model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}