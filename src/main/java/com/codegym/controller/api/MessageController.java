package com.codegym.controller.api;

import com.codegym.model.Category;
import com.codegym.model.Message;
import com.codegym.services.impl.MessageServiceImpl;
import com.codegym.services.impl.PostContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api")
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    private PostContentServiceImpl postContentService;

    @GetMapping("/messages/")
    private ResponseEntity<List<Message>> listMessages() {
        List<Message> messages = messageService.findAll();
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    @GetMapping(value = "/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessage(@PathVariable("id") long id) {
        Message message = messageService.findById(id);
        if (message == null) {
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

//    @RequestMapping(value = "/messages/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Message> createMessage(@RequestBody Message message, UriComponentsBuilder ucBuilder) {
//        message.setDate(LocalDateTime.now());
//        try {
//            messageService.save(message);
//            return new ResponseEntity<Message>(message, HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping(value = "/messagesS/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Message>> ArtMessage(@PathVariable("id") long id) {
        Set<Message> message = postContentService.findById(id).getMessages();
        return new ResponseEntity<Set<Message>>(message, HttpStatus.OK);
    }
    @PostMapping(value = "/messages/")
    public ResponseEntity<Message> createCategory(@RequestBody Message message, UriComponentsBuilder ucBuilder) {
        message.setDate(LocalDate.now());
        try {
            messageService.save(message);
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/messages/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") long id, @RequestBody Message message) {

        Message currentMessage = messageService.findById(id);


        if (currentMessage == null) {
            System.out.println("Message with id " + id + " not found");
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
        }

        currentMessage.setName(message.getName());
        currentMessage.setEmail(message.getEmail());
        currentMessage.setWriteComment(message.getWriteComment());
        currentMessage.setPost(message.getPost());


        try {
            messageService.save(currentMessage);
        } catch (Exception ex) {
            return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Message>(currentMessage, HttpStatus.OK);
    }

    @DeleteMapping(value = "/messages/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable("id") Long id) {
        Message currentMessage = messageService.findById(id);
        if (currentMessage == null) {
            System.out.println("Message with id " + id + " not found");
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
        }
        try {
            messageService.remove(id);

        } catch (Exception e) {
            return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Message>(currentMessage, HttpStatus.OK);
    }

    }