package com.chiliexe.springBlog.controllers;

import javax.validation.Valid;

import com.chiliexe.springBlog.models.Category;
import com.chiliexe.springBlog.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
	private CategoryService categoryService;

    @GetMapping("/create/category")
	public ModelAndView createCategory(Category category)
	{
		return new ModelAndView("Admin/category");
	}

	@PostMapping("/create/category")
	public ModelAndView saveCategory(@Valid Category category, BindingResult result, RedirectAttributes attr)
	{
		if(result.hasErrors()) return createCategory(category);

		categoryService.save(category);
		attr.addFlashAttribute("msg", "Categoria adcionada com sucesso");
		ModelAndView mv = new ModelAndView("redirect:/admin/create/category");
		return mv;
	}
}
