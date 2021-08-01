package com.chiliexe.springBlog.controllers;

import java.io.IOException;

import javax.validation.Valid;

import com.chiliexe.springBlog.models.Post;
import com.chiliexe.springBlog.models.User;
import com.chiliexe.springBlog.services.CategoryService;
import com.chiliexe.springBlog.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuario")
public class UserController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

	@GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal User user)
    {	
		ModelAndView mv = new ModelAndView("User/index");
		mv.addObject("posts", postService.findByUser(user));
		mv.addObject("user", user);
        return mv;
    }

	@GetMapping("/editar/dados")
	public ModelAndView editUserData(@AuthenticationPrincipal User user)
	{
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	@PostMapping("/editar/dados")
	public ModelAndView saveEditUserData(@Valid User user, BindingResult result)
	{
		if(result.hasErrors()) return editUserData(user);

		ModelAndView mv = new ModelAndView();
		return mv;
	}

    @GetMapping("/criar/post")
	public ModelAndView createPost(Post post)
	{	
		ModelAndView mv = new ModelAndView("User/post");
		mv.addObject("pagePost", true);
		mv.addObject("categories", categoryService.findAll());
		return mv;
	}
	
	@PostMapping("/criar/post")
	public ModelAndView savePost(@Valid Post post, BindingResult result, @AuthenticationPrincipal User user,
		 @RequestParam MultipartFile file) throws IOException
	{
		if(result.hasErrors()) return createPost(post);
		
		var savedPost = postService.save(post, file, user);
		return new ModelAndView("redirect:/post/" + savedPost.getSlug());
	}
}
