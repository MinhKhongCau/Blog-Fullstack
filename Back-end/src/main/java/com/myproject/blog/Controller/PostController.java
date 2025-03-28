package com.myproject.blog.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.myproject.blog.Model.Account;
import com.myproject.blog.Model.Post;
import com.myproject.blog.Service.AccountService;
import com.myproject.blog.Service.PostService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/post/{id}")
	public String getMethodName(@PathVariable Long id, Model model, Principal principal) {

		Optional<Post> optionalPost = postService.getById(id);
		String authUser = "email";

		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			model.addAttribute("post", post);

			// get user of current logged in session user
			if (principal != null) {
				authUser = principal.getName();
			}
			if (authUser.equals(post.getAuthor().getEmail())) {
				model.addAttribute("isOwner", true);
			} else {
				model.addAttribute("isOwner", false);
			}

			return "post_view/post";
		} else {
			return "404";
		}
	}

	@GetMapping("/post/add")
	public String addPostHandler(Model model, Principal principal) {
		String authUser = "email";

		if (principal != null) {
			authUser = principal.getName();
		}

		Optional<Account> accouOptional = accountService.getByEmail(authUser);
		if (accouOptional.isPresent()) {
			Post post = new Post();
			post.setAuthor(accouOptional.get());
			model.addAttribute("post", post);
			return "post_view/post_add";
		} else {
			return "404";
		}
	}

	@PostMapping("/post/add")
	@PreAuthorize("isAuthenticated()")
	public String postAddPostHandler(@ModelAttribute Post post, Principal principal, BindingResult result) {
		if (result.hasErrors()) {
			return "post_view/post";
		}
		String authUser = "emal";
		if (principal != null) {
			authUser = principal.getName();
		}
		if (post.getAuthor().getEmail().compareToIgnoreCase(authUser) < 0) {
			return "redirect:/?error";
		}
		postService.save(post);
		System.out.println("***" + post.getId() + post.getTitle());
		return "redirect:/post/" + post.getId();
	}

	@GetMapping("/post/{id}/edit")
	@PreAuthorize("isAuthenticated()")
	public String editPostHandler(@PathVariable Long id, Model model, Principal principal) {
		String authUser = "email";

		if (principal != null) {
			authUser = principal.getName();
		}

		Optional<Account> accouOptional = accountService.getByEmail(authUser);
		if (accouOptional.isPresent()) {
			Optional<Post> post = postService.getById(id);
			if (post.isPresent())
				model.addAttribute("post", post.get());
			return "post_view/post_edit";
		} else {
			return "404";
		}
	}

	@PostMapping("/post/edit")
	@PreAuthorize("isAuthenticated()")
	public String postEditPostHandler(@ModelAttribute Post post, Principal principal) {
		String authUser = "emal";
		if (principal != null) {
			authUser = principal.getName();
		}
		if (post.getAuthor().getEmail().compareToIgnoreCase(authUser) < 0) {
			return "redirect:/?error";
		}
		LocalDateTime localDTime = LocalDateTime.now();
		post.setUpdateAt(localDTime);

		postService.save(post);
		System.out.println("***" + post.getId() + post.getTitle());
		return "redirect:/post/" + post.getId();
	}

	@GetMapping("/post/{id}/delete")
	@PreAuthorize("isAuthenticated()")
	public String deletePostHandler(@PathVariable Long id, Model model, Principal principal) {
		String authUser = "email";

		if (principal != null) {
			authUser = principal.getName();
		}

		Optional<Account> accouOptional = accountService.getByEmail(authUser);
		if (accouOptional.isPresent()) {
			Optional<Post> post = postService.getById(id);
			if (post.isPresent())
				postService.delete(post.get());
			return "redirect:/home";
		} else {
			return "404";
		}
	}
}
