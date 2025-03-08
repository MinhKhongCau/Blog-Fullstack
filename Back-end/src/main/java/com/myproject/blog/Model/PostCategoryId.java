package com.myproject.blog.Model;

import jakarta.persistence.Embeddable;

@Embeddable
class PostCategoryId {
	private Integer idPost;
	private Integer idCategory;
}
