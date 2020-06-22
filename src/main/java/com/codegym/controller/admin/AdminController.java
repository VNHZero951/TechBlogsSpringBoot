package com.codegym.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("admin/category")
    public String index() {
        return "admin/category/index";
    }

    @GetMapping("admin/tag")
    public String tags() {
        return "admin/tag/tag";
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }
    @GetMapping("/")
    public String home() {
        return "user/index";
    }

    @GetMapping("admin/message")
    public String message() {
        return "admin/message/message";
    }
}
