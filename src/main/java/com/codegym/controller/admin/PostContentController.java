package com.codegym.controller.admin;

import com.codegym.model.Post;
import com.codegym.services.impl.PostContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class PostContentController extends AdminBaseController {
    private final String TERM = "Posts Manager";

    @Autowired
    private PostContentServiceImpl postContentService;


    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Post.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/post")
    public ModelAndView index() {

        List<Post> posts = postContentService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/post/index");
        modelAndView.addObject("getPost", posts);
        modelAndView.addObject("title", TITLE_ADD);
        modelAndView.addObject("term", TERM);
        return modelAndView;
    }
    @GetMapping("/post/add")
    public ModelAndView showAddForm() {
        ModelAndView modelAndView = new ModelAndView("admin/post/add");
        modelAndView.addObject("addPost", new Post());
        modelAndView.addObject("action", ACTION_ADD);
        modelAndView.addObject("term", TERM);
        modelAndView.addObject("title", TITLE_ADD);
        modelAndView.addObject("message",null);
        return modelAndView;
    }
    @PostMapping("/post/add")
    public ModelAndView saveAddForm(HttpServletRequest request,@ModelAttribute("post") Post post){
        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = post.getFileImage();
        //
        Map<File, String> uploadedFiles = new HashMap();
        for (CommonsMultipartFile fileData : fileDatas) {

            // Tên file gốc tại Client.
            String name = fileData.getOriginalFilename();
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {
                try {
                    // Tạo file tại Server.
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    // Luồng ghi dữ liệu vào file trên Server.
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                    post.setImage(name);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                }
            }
        }
        //
        post.setDate(LocalDateTime.now());
        post.setNumberLike(0L);
        post.setNumberView(0L);
        postContentService.save(post);

        ModelAndView modelAndView = new ModelAndView("admin/post/add");
        modelAndView.addObject("addPost",new Post());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("alert",ALERT_SUCCESS);

        modelAndView.addObject("message", ACTION_ADD_SUCCESS);

        return  modelAndView;
    }
    @PostMapping("/post/edit")
    public ModelAndView saveEditForm(HttpServletRequest request,@ModelAttribute("post") Post post){
        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = post.getFileImage();
        //
        if (fileDatas!=null){
            Map<File, String> uploadedFiles = new HashMap();
            for (CommonsMultipartFile fileData : fileDatas) {

                // Tên file gốc tại Client.
                String name = fileData.getOriginalFilename();
                System.out.println("Client File Name = " + name);

                if (name != null && name.length() > 0) {
                    try {
                        // Tạo file tại Server.
                        File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                        // Luồng ghi dữ liệu vào file trên Server.
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                        stream.write(fileData.getBytes());
                        stream.close();
                        //
                        post.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        //
        postContentService.save(post);
        post.setNumberLike(0L);
        post.setNumberView(0L);
        //
        ModelAndView modelAndView = new ModelAndView("/admin/post/add");
        modelAndView.addObject("addPost",post);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return  modelAndView;
    }

    @GetMapping("/post/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        Post post = postContentService.findById(id);
        if(post != null) {

            ModelAndView modelAndView = new ModelAndView("admin/post/add");
            modelAndView.addObject("addPost",post);
            modelAndView.addObject("action",ACTION_EDIT);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_EDIT);

            return  modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error/pages-404");
            return modelAndView;
        }

    }

    @GetMapping("/post/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Post post = postContentService.findById(id);
        if( post != null) {
            ModelAndView modelAndView = new ModelAndView("admin/post/delete");

            modelAndView.addObject("deletePost",post);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error/pages-404");
            return modelAndView;
        }
    }

    @PostMapping("/post/delete")
    public String deleteProvince(@ModelAttribute("post") Post post){
        postContentService.remove(post.getId());
        return "redirect:/admin/post";
    }
}
