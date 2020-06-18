package com.codegym.controller.user;

import com.codegym.model.Category;
import com.codegym.model.Post;
import com.codegym.repository.CategoryRepository;
import com.codegym.repository.PostRepository;
import com.codegym.services.impl.CategorySeviceImpl;
import com.codegym.services.impl.PostContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Direction.values;

@Controller
@RequestMapping("/category")
public class UserCategoryController {

    @Autowired
    private CategorySeviceImpl categorySevice;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostContentServiceImpl postContentService;

    @GetMapping("/{category}")
    public ModelAndView tech(@RequestParam(defaultValue = "0") int page,@PathVariable("category") String category){
        ModelAndView modelAndView = new ModelAndView("/user/category");
        modelAndView.addObject("layout",categoryRepository.findAll());
        switch (category)
        {
            case "1":
                modelAndView.addObject("dev",postRepository.findAllByCategoryAndIdBy1(PageRequest.of(page,4)));
                modelAndView.addObject("categoryByGetID",categoryRepository.findAll().get(0).getId());
                modelAndView.addObject("categoryByGetName",categoryRepository.findAll().get(0).getTitle());
                return modelAndView;
            case "2":
                modelAndView.addObject("tech",postRepository.findAllByCategoryAndIdBy2(PageRequest.of(page,4)));
                modelAndView.addObject("categoryByGetID",categorySevice.findAll().get(1).getId());
                modelAndView.addObject("categoryByGetName",categoryRepository.findAll().get(1).getTitle());
                return modelAndView;
            case "3":
                modelAndView.addObject("tools",postRepository.findAllByCategoryAndIdBy3(PageRequest.of(page,4)));
                modelAndView.addObject("categoryByGetID",categorySevice.findAll().get(2).getId());
                modelAndView.addObject("categoryByGetName",categoryRepository.findAll().get(2).getTitle());
                return modelAndView;
        }
        return modelAndView;
    }
}
