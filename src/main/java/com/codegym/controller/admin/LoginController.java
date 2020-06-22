package com.codegym.controller.admin;

import com.codegym.model.User;
import com.codegym.repository.UserRepository;
import com.codegym.services.impl.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailImpl userDetail;

    @GetMapping("login")
    public ModelAndView getLogin(){
        ModelAndView modelAndView = new ModelAndView("admin/login");
        return modelAndView ;
    }
    @PostMapping("login")
    public ModelAndView postLogin(){
        ModelAndView modelAndView = new ModelAndView("admin/login");
        return modelAndView;
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    @GetMapping("admin/profile/{id}")
    public ModelAndView getProfile(@PathVariable long id){
        User users = userDetail.findById(id);
        ModelAndView modelAndView = new ModelAndView("admin/profile");
        modelAndView.addObject("profile",users);
        return modelAndView;
    }
    @GetMapping("admin/layout/{id}")
    public ModelAndView getLayout(@PathVariable long id){
        User users = userDetail.findById(id);
        ModelAndView modelAndView = new ModelAndView("admin/layout");
        modelAndView.addObject("layout",users);
        return modelAndView;
    }
}
