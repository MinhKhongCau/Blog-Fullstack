package com.myproject.blog.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.blog.Model.Post;
import com.myproject.blog.Service.PostService;

@Controller
public class HomeController {
	@Autowired
	private PostService postService;

	@GetMapping("/")
	public String firstPage() {
		return "index";
	}

	@GetMapping("/home")
	@ResponseBody
	public Page<Post> home(@RequestParam(required = false, defaultValue = "createAt", name = "sort_by") String sort_by,
			@RequestParam(required = false, defaultValue = "1", name = "page") String page,
			@RequestParam(required = false, defaultValue = "2", name = "per_page") String per_page) {
		Page<Post> posts = postService.getByPage(Integer.parseInt(page) - 1, Integer.parseInt(per_page), sort_by);

		System.out.println(per_page);
		int total_page = posts.getTotalPages();
		List<Integer> pages = new ArrayList<>();

		if (total_page > 0) {
			pages = IntStream.rangeClosed(0, total_page - 1).boxed().toList();
		}

		List<String> links = new ArrayList<>();
		if (pages != null) {
			for (int link : pages) {
				String active = "";
				if (link == posts.getNumber()) {
					active = "active";
				}
				String link_patigation = "/home?per_page=" + per_page + "&page=" + (link + 1) + "&sort_by=" + sort_by;
				links.add(String.format("<li class=\"page-item %s\"><a class=\"page-link\" href=\"%s\">%s</a></li>",
						active, link_patigation, (link + 1)));
			}
			// model.addAttribute("links", links);
		}

		for (Post post : posts) {
			System.out.println("***" + post.getId() + " " + post.getTitle() + " " + post.getAuthor().getAuthorities());
		}
		return posts;
	}

	@GetMapping("/about")
	public String about(Model model) {
		return "about";
	}
}
