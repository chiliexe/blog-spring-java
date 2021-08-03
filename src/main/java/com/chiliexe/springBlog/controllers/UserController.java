package com.chiliexe.springBlog.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.chiliexe.springBlog.models.Post;
import com.chiliexe.springBlog.models.User;
import com.chiliexe.springBlog.services.CategoryService;
import com.chiliexe.springBlog.services.PostService;
import com.chiliexe.springBlog.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuario")
public class UserController {
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

	@Autowired
	private UserService userService;

	@GetMapping()
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
		if(user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		ModelAndView mv = new ModelAndView("User/editData");
		mv.addObject("user", user);
		return mv;
	}
	@PostMapping("/editar/dados")
	public ModelAndView saveEditUserData(@Valid User user, @AuthenticationPrincipal User currentUser, 
		 BindingResult result, HttpServletRequest r) throws ServletException
	{
		if(result.hasErrors()) 
			return editUserData(currentUser);
		if(user.getId() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		if(!user.getId().equals(currentUser.getId())) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		userService.update(user);
		r.logout();
		ModelAndView mv = new ModelAndView("redirect:/usuario/");
		return mv;
	}

	@GetMapping("/editar/post/{slug}")
	public ModelAndView editPost(@PathVariable String slug, @AuthenticationPrincipal User user)
	{
		ModelAndView mv = new ModelAndView("User/editPost");
		Post post = postService.findByUserAndSlug(user, slug);
		mv.addObject("post", post);
		mv.addObject("categories", categoryService.findAll());
		mv.addObject("pagePost", true);
		return mv;
	}

	@PostMapping("/editar/post/{slug}")
	public ModelAndView saveEditPost( @PathVariable String slug, @RequestParam MultipartFile file,
	 @Valid Post post, @AuthenticationPrincipal User user, BindingResult result)
	{
		if(result.hasErrors()) return editPost(post.getSlug(), user);

		//CHECK IF USER AND SLUG ARE STILL THE SAME
		postService.findByUserAndSlug(user, slug);

		Post updatedPost = postService.update(post, file, user, slug);
		ModelAndView mv = new ModelAndView("redirect:/post/" + updatedPost.getSlug());
		mv.addObject("post", updatedPost);
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
