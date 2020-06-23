package com.codegym.controller.api;

import com.codegym.model.Post;
import com.codegym.services.impl.PostContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {

    @Autowired
    private PostContentServiceImpl postContentService;

    @GetMapping("/postContent")
    private ResponseEntity<List<Post>> listTags() {
        List<Post> posts = postContentService.findAll();
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/postContent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getProduct(@PathVariable("id") long id) {
        Post post = postContentService.findById(id);
        if (post == null) {

            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PostMapping(value = "/postContent/")
    public ResponseEntity<Post> createPostContent(@RequestBody Post post, UriComponentsBuilder ucBuilder) {
        try {
            postContentService.save(post);
            return new ResponseEntity<Post>(post, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/postContent/{id}")
    public ResponseEntity<Post> updatePostContent(@Valid @PathVariable("id") long id, @RequestBody Post product) {

        Post post = postContentService.findById(id);

        if (post == null) {
            System.out.println("postContent with id " + id + " not found");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        post.setTitle(post.getTitle());
        post.setDescription(post.getDescription());
        post.setContent(post.getContent());
        post.setImage(post.getImage());
        try {
            postContentService.save(post);
        }catch (Exception ex){
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
    @DeleteMapping(value = "/postContent/{id}")
    public ResponseEntity<Post> deletePostContent(@PathVariable("id") Long id) {
        Post post = postContentService.findById(id);
        if (post == null) {
            System.out.println("postContent with id " + id + " not found");
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        try {
            postContentService.remove(id);

        } catch (Exception e) {
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
}
