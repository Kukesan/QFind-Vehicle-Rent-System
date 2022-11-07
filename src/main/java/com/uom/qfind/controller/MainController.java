package com.uom.qfind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.uom.qfind.model.Admin;
import com.uom.qfind.service.AdminService;


@Controller
public class MainController {
	@Autowired
	private AdminService adminService;
	
	 @GetMapping("/vehicle-view")
	  public String showAll(Model model) {
	      return "vehicle.html";
	  }
	
	 @GetMapping("/user-view")
	  public String showUsers(Model model) {
	      return "users.html";
	  }
	 
	 @GetMapping("/vehicle-booked-view")
	  public String showBooked(Model model) {
	      return "booked-vehicle.html";
	  }
	 
	 @GetMapping("/login")
	    public String login(Model model) {
			  model.addAttribute("admin", new Admin());

	        return "login.html";
	    }

	@PostMapping("/home")
	  public String greetingSubmit(@ModelAttribute Admin admin, Model model) {
			model.addAttribute("admin", admin);

				if(adminService.findAdmin(admin.getUsername(), admin.getPassword())) {

				System.out.print("##########"+ admin.getUsername());

				return "vehicle.html";
			}
			return "error.html";
	  }
	
}
