package com.chiliexe.springBlog.controllers;

import java.util.List;

import javax.validation.Valid;

import com.chiliexe.springBlog.models.Comment;
import com.chiliexe.springBlog.models.Post;
import com.chiliexe.springBlog.services.CommentService;
import com.chiliexe.springBlog.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//TODO admin panel, edit user, edit post, form login, paination

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/")
	public ModelAndView index()
	{
		ModelAndView mv = new ModelAndView("Home/index");
		List<Post> posts = postService.findAll();
		mv.addObject("posts", posts);
		return mv;
	}

	//teste@123
	@GetMapping("/post/{slug}")
	public ModelAndView findPost(@PathVariable String slug, Comment comment)
	{
		Post post = postService.findBySlug(slug);
		ModelAndView mv = new ModelAndView("Home/detail");
		mv.addObject("post", post);
		return mv;
	}

	@PostMapping("/post/comentario/{slug}")
	public ModelAndView saveComment(@PathVariable String slug, @Valid Comment comment, BindingResult result)
	{
		if(result.hasErrors()) return findPost(slug, comment);
		var post = postService.findBySlug(slug);
		commentService.save(comment, post);
		return new ModelAndView("redirect:/post/" + slug);
	}

	@GetMapping("/categoria/{category}")
	public ModelAndView byCategory(@PathVariable String category)
	{
		ModelAndView mv = new ModelAndView("Home/byCategory");
		List<Post> posts = postService.findByCategory(category);
		mv.addObject("posts", posts);
		return mv;
	}

}
