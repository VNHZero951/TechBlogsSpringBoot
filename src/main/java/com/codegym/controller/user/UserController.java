package com.codegym.controller.user;

import com.codegym.model.Post;
import com.codegym.model.Tag;
import com.codegym.repository.PostRepository;
import com.codegym.services.impl.CategorySeviceImpl;
import com.codegym.services.impl.MessageServiceImpl;
import com.codegym.services.impl.PostContentServiceImpl;
import com.codegym.services.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private PostContentServiceImpl postContentService;

//    @Autowired
//    private PostRepository postContentRepository;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategorySeviceImpl categoryService;
    @Autowired
    private MessageServiceImpl messageService;

//    @Autowired
//    private ContactSevice contactSevice;

    @GetMapping()
    public ModelAndView dashboard(@RequestParam(defaultValue = "0") int page){
        ModelAndView modelAndView = new ModelAndView("user/index");
        List<Post> postContentList = postRepository.findPostLimit();

        List<Post> contentList =postRepository.findCategoriesById();
        List<Post> slidePost = new ArrayList<>();
        List<Post> haithangcuoi = new ArrayList<>();
        for (int i =0; i<contentList.size(); i++) {
            if(i<3){
                slidePost.add(contentList.get(i));
            }else {
                if (haithangcuoi.size() < 4) {
                    haithangcuoi.add(contentList.get(i));
                }
                else {
                    break;
                }
            }
        }

        List<Post> categoriesbyid2 = postRepository.categorybyid2();
        List<Post> first3post = new ArrayList<>();
        List<Post> last2post = new ArrayList<>();
        for (int i =0; i<categoriesbyid2.size(); i++) {
            if(i<3){
                first3post.add(categoriesbyid2.get(i));
            }else {
                if (last2post.size() < 4) {
                    last2post.add(categoriesbyid2.get(i));
                }
                else {
                    break;
                }
            }
        }
        List<Post> categoriesbyid3 = postRepository.categorybyid3();
        List<Post> first3postid3 = new ArrayList<>();
        List<Post> last2postid3 = new ArrayList<>();
        for (int i =0; i<categoriesbyid3.size(); i++) {
            if(i<3){
                first3postid3.add(categoriesbyid3.get(i));
            }else {
                if (last2postid3.size() < 4) {
                    last2postid3.add(categoriesbyid3.get(i));
                }
                else {
                    break;
                }
            }
        }
        postContentList.remove(0);
//        Page<Post> findallbylimit = postRepository.findAll(PageRequest.of(0,5));
        Page<Post> newpost3 = postRepository.findByPostOrderByDateDesc(PageRequest.of(0,2));
//        List<Post> findbytitle = postContentRepository.findByPostOrderByDateDesc1();



        List<Post> findall =postRepository.finddnew();

        findall.remove(1);
        findall.remove(0);





        Page<Post> PageSort = postRepository.page(PageRequest.of(page,4));



        List<Post> findmostview = postRepository.findmostview();

        Page<Post> findnewpostby5 = postRepository.findnewpostby5(PageRequest.of(0,4));
        Page<Post> findtrendingpost = postRepository.findtrendingpost(PageRequest.of(0,3));

        modelAndView.addObject("postHeader",postRepository.findnewPost());
        modelAndView.addObject("postHome",postContentList);
        modelAndView.addObject("postcontentbycate",slidePost);
        modelAndView.addObject("postcontentbylast",haithangcuoi);
        modelAndView.addObject("first3post",first3post);
        modelAndView.addObject("last2post",last2post);
        modelAndView.addObject("first3postid3",first3postid3);
        modelAndView.addObject("last2postid3",last2postid3);
        modelAndView.addObject("newpost3",newpost3);
        modelAndView.addObject("findall",findall);
        modelAndView.addObject("findmostview",findmostview);
        modelAndView.addObject("findnewpostby5",findnewpostby5);
        modelAndView.addObject("findtrendingpost",findtrendingpost);
        modelAndView.addObject("data",PageSort);
        modelAndView.addObject("layout",categoryService.findAll());
//        modelAndView.addObject("data",postContentRepository.page(PageRequest.of(page,4)));


        return modelAndView;
    }

//        @GetMapping("/contact")
//    public ModelAndView contact(){
//
//        ModelAndView modelAndView = new ModelAndView("/user/contact");
//        modelAndView.addObject("contact", new Contact());
//        return modelAndView;
//    }
//    @Autowired
//    private JavaMailSender mailSender;
//    @PostMapping("/contact")
//    public ModelAndView saveMessageContact(@ModelAttribute("contact") Contact contact, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView("/user/contact");
//        modelAndView.addObject("contact", new Contact());
//        if (bindingResult.hasFieldErrors()) {
//            return modelAndView;
//        } else {
//            contactSevice.save(contact);
//            SimpleMailMessage crunchifyMsg = new SimpleMailMessage();
//            crunchifyMsg.setFrom("2838d82328-8590c0@inbox.mailtrap.io");
//            crunchifyMsg.setTo(contact.getEmail());
//            crunchifyMsg.setText(contact.getText());
//            mailSender.send(crunchifyMsg);
//            return modelAndView;
//        }
//    }
//    @GetMapping("/login")
//    public ModelAndView login(){
//        ModelAndView modelAndView = new ModelAndView("user/login");
//        return modelAndView;
//    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout (request, response, auth);
//        }
//        return "redirect:/";
//    }
    @GetMapping("view/{id}")
    public ModelAndView dashboard1(@PathVariable("id") Long id){

        ModelAndView modelAndView = new ModelAndView("user/single-post");

        Post postContents = postContentService.findById(id);
        postContents.setNumberView(postContents.getNumberView()+1);
        List<Tag> tagList = tagService.findTagByContentId(id);
        List<Post> category=postRepository.findCategory(postContents.category.getId());
        List<Post> listMoi = new ArrayList<>();

        postRepository.save(postContents);
        category.removeIf(postContent -> postContent.getId().equals(postContents.getId()));
        if(category.size()>=2) {
//            int length=category.size();
//            int new1 = (int) (Math.random() *length);


            listMoi.add(category.get(0));
//            int new2 = (int) (Math.random() *length);
//            for (int i = 0 ; i<=length;i++){
//                if(new1==new2){
//                    new2 = (int)Math.random()*length;
//                }else {
//                    break;
//                }
//                break;
//            }

            listMoi.add(category.get(1));
        }

        modelAndView.addObject("postContent", postContents);
        modelAndView.addObject("tag",tagList);
        modelAndView.addObject("postCate",listMoi);
        modelAndView.addObject("views",postContents.getNumberView());
        return modelAndView;
    }
}