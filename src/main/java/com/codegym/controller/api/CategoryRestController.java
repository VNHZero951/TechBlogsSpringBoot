package com.codegym.controller.api;

import com.codegym.model.Category;
import com.codegym.model.Tag;
import com.codegym.services.impl.CategorySeviceImpl;
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
public class CategoryRestController {
    @Autowired
    private CategorySeviceImpl categorySevice;

    @GetMapping("/category")
    private ResponseEntity<List<Category>> listCategory() {
        List<Category> categories = categorySevice.findAll();
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategory(@PathVariable("id") long id) {
        Category category = categorySevice.findById(id);
        if (category == null) {

            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @RequestMapping(value = "/category/", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@RequestBody Category category, UriComponentsBuilder ucBuilder) {
        try {
            categorySevice.save(category);
            return new ResponseEntity<Category>(category, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Category>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/category/{id}")
    public ResponseEntity<Category> updateCategory(@Valid @PathVariable("id") long id, @RequestBody Category category) {

        Category categorySeviceById = categorySevice.findById(id);

        if (category == null) {
            System.out.println("postContent with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        categorySeviceById.setTitle(categorySeviceById.getTitle());
        try {
            categorySevice.save(category);
        }catch (Exception ex){
            return new ResponseEntity<Category>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }
    @DeleteMapping(value = "/category/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") Long id) {
        Category category = categorySevice.findById(id);
        if (category == null) {
            System.out.println("postContent with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        try {
            categorySevice.remove(id);

        } catch (Exception e) {
            return new ResponseEntity<Category>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }
}
