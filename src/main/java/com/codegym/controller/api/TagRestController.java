package com.codegym.controller.api;
import com.codegym.model.Tag;
import com.codegym.services.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
@RestController
@RequestMapping(value = "/api")
public class TagRestController {
    @Autowired
    private TagServiceImpl tagService;
    @GetMapping("/tags/")
    private ResponseEntity<List<Tag>> listTags() {
        List<Tag> tags = tagService.findAll();
        return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
    }
    @GetMapping(value = "/tags/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> getTag(@PathVariable("id") long id) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Tag>(tag, HttpStatus.OK);
    }
    @RequestMapping(value = "/tags/", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag, UriComponentsBuilder ucBuilder) {
        try {
            tagService.save(tag);
            return new ResponseEntity<Tag>(tag, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/tags/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tag) {
        Tag currentTag = tagService.findById(id);
        if (currentTag == null) {
            System.out.println("Tag with id " + id + " not found");
            return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);
        }
        currentTag.setTitle(tag.getTitle());
        try {
            tagService.save(currentTag);
        } catch (Exception ex) {
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Tag>(currentTag, HttpStatus.OK);
    }
    @DeleteMapping(value = "/tags/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable("id") Long id) {
        Tag currentTag = tagService.findById(id);
        if (currentTag == null) {
            System.out.println("Tag with id " + id + " not found");
            return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);
        }
        try {
            tagService.remove(id);
        } catch (Exception e) {
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Tag>(currentTag, HttpStatus.OK);
    }
//    @PostMapping("/tags")
//    public Tag add(@RequestBody Tag  tag){
//        tagService.save(tag);
//        return tag;
//    }
//    @GetMapping(value = "/like/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PostContent> like(@PathVariable("id") long id) {
//        PostContent postContent = postContentService.findById(id);
//        postContent.setNumberLike(postContent.getNumberLike()+1);
//        postContentService.save(postContent);
//        return new ResponseEntity<PostContent>(postContent, HttpStatus.OK);
//    }
}