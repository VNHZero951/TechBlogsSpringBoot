package com.codegym.controller.user;

import com.codegym.model.Category;
import com.codegym.services.impl.CategorySeviceImpl;
import com.codegym.services.impl.PostContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private PostContentServiceImpl postContentService;

    @Autowired
    private CategorySeviceImpl categorySevice;

    @GetMapping("/")
    public ModelAndView homePage() {

        ModelAndView modelAndView = new ModelAndView("user/index");
        modelAndView.addObject("layout",categorySevice.findAll());
        return modelAndView;
    }


}
