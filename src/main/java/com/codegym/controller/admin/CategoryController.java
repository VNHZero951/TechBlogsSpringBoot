package com.codegym.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @GetMapping("/admin/category")
    public String index() {
        return "admin/category/index";
    }

    @GetMapping("/tags")
    public String tags() {
        return "admin/tag/tag";
    }
}
