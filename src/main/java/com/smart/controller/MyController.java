package com.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserReposetory;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
public class MyController {

	@Autowired
	private UserReposetory userReposetory;

	@RequestMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "Home - Smart Content Project");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "About - Smart Content Project");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("title", "Register - Smart Content Project");
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model , HttpSession session) {

		try {
			
			if(!agreement) {
				System.out.println("You have not agreed term and condirions ");
				throw new Exception("You have not agreed term and condirions ");
				
			}
			user.setEnable(true);
			user.setRole("Role_User");
			user.setImageUrl("default.png");
			
			System.out.println("Agreement:" + agreement);
			System.out.println("User" + user);

			User result = this.userReposetory.save(user);

			model.addAttribute("user" + new User());
			
			session.setAttribute("message", new Message("Succesfull Registered !! " , "alert-success"));
			return "signup";
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong !! " + e.getMessage(), "alert-danger"));
			return "signup";
		}
		
		
	}

}
