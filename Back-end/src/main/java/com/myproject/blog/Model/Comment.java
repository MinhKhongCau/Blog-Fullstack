package com.myproject.blog.Model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String body;
	private Date commentAt;
	private Date updateAt;
	private Date deleteAt;

	@ManyToOne
	@JoinColumn(name = "ID_ACCOUNT")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "ID_POST")
	private Post post;
}
