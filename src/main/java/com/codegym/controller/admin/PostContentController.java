package com.codegym.controller.admin;

import com.codegym.model.Category;
import com.codegym.model.Post;
import com.codegym.model.Post_Tag;
import com.codegym.model.Tag;
import com.codegym.repository.PostRepository;
import com.codegym.services.impl.CategorySeviceImpl;
import com.codegym.services.impl.PostContentServiceImpl;
import com.codegym.services.impl.Post_TagServiceImpl;
import com.codegym.services.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/admin")
public class PostContentController extends AdminBaseController {
    private final String TERM = "Posts Manager";

    @Autowired
    private PostContentServiceImpl postContentService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    Post_TagServiceImpl post_tagService;

    @Autowired
    private CategorySeviceImpl categorySevice;


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
//    @GetMapping("/post")
//    public String viewHomePage(Model model) {
//        return findPaginated(1, "category", "asc", model);
//    }
    @GetMapping("/post")
    public ModelAndView index(@PageableDefault(size = 5,value = 0,sort = "id",direction = DESC) Pageable pageable) {

//        List<Post> posts = postContentService.findAll();
        Page<Post> page = postRepository.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("admin/post/index");
//        modelAndView.addObject("getPost", posts);
        modelAndView.addObject("page",page);
        modelAndView.addObject("categoryList",categorySevice.findAll());
        modelAndView.addObject("title", TITLE_ADD);
        modelAndView.addObject("term", TERM);
        return modelAndView;
    }
    @GetMapping("/post/add")
    public ModelAndView showAddForm() {
        List<Category> categoryList = categorySevice.findAll();
        Iterable<Tag> tags = tagService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/post/add");
        modelAndView.addObject("addPost", new Post());
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.addObject("tag",tags);
        modelAndView.addObject("action", ACTION_ADD);
        modelAndView.addObject("term", TERM);
        modelAndView.addObject("title", TITLE_ADD);
        return modelAndView;
    }
    @PostMapping("/post/add")
    public ModelAndView saveAddForm(HttpServletRequest request,@Valid @ModelAttribute("post") Post post,@RequestParam("tags")Long[] listTag){
        //
        List<Category> categoryList = categorySevice.findAll();
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

        Post postContents =postContentService.findId();
        for( int i=0; i<listTag.length; i++){
            System.out.println(listTag[i]+',');
            Post_Tag post_tag =new Post_Tag();
            post_tag.setTag( tagService.findById(listTag[i]));
            post_tag.setPost(postContents);
            post_tagService.save(post_tag);
        }

        ModelAndView modelAndView = new ModelAndView("admin/post/add");
        modelAndView.addObject("addPost",new Post());
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("alert",ALERT_SUCCESS);

        modelAndView.addObject("message", ACTION_ADD_SUCCESS);

        return  modelAndView;
    }
    @PostMapping("/post/edit")
    public ModelAndView saveEditForm(HttpServletRequest request,@Valid @ModelAttribute("post") Post post, @RequestParam("tags")Long[] listTag){
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

        List<Tag> tagList = tagService.findTagByContentId(post.getId());
        for (int i=0; i<listTag.length;i++) {
            boolean checkAdd = false;
            for (int j = 0; j < tagList.size(); j++) {
                if (tagList.get(j).getId().equals(listTag[i])) {
                    checkAdd = true;
                }
            }
            if(!checkAdd){
                Post_Tag post_tag =new Post_Tag();
                post_tag.setTag( tagService.findById(listTag[i]));
                post_tag.setPost(post);
                post_tagService.save(post_tag);
            }
        }
        for (int i=0; i<tagList.size();i++) {
            boolean checkDelete = false;
            for (int j = 0; j < listTag.length; j++) {
                if (tagList.get(i).getId().equals(listTag[j])) {
                    checkDelete = true;
                }
            }
            if (!checkDelete){
                Post_Tag post_tag = post_tagService.findByIdByPost_tag(post.getId(),tagList.get(i).getId());
                post_tagService.remove(post_tag.getId());
            }
        }

        ModelAndView modelAndView = new ModelAndView("/admin/post/add");
        modelAndView.addObject("addPost",post);
        modelAndView.addObject("categoryList",categorySevice.findAll());
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
            List<Tag> tags = tagService.findAll();
            for (Tag tag : tags
            ) {
                for (Post_Tag t: post.post_tags
                ) {
                    if (t.tag.getId()==tag.getId())
                        tag.setSelected(true);
                }
            }
            ModelAndView modelAndView = new ModelAndView("admin/post/add");
            modelAndView.addObject("categoryList",categorySevice.findAll());
            modelAndView.addObject("addPost",post);
            modelAndView.addObject("tag",tags);
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
            List<Tag> tags = tagService.findAll();
            ModelAndView modelAndView = new ModelAndView("admin/post/delete");

            modelAndView.addObject("deletePost",post);
            modelAndView.addObject("categoryList",categorySevice.findAll());
            modelAndView.addObject("tag",tags);
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
