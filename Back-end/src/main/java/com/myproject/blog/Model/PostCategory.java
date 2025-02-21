package com.myproject.blog.Model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "POST_CATEGORY")
public class PostCategory {
    @EmbeddedId
    private PostCategoryId id;
}
