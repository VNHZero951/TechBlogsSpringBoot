package com.codegym.controller.user;

import com.codegym.model.Contact;
import com.codegym.repository.CategoryRepository;
import com.codegym.services.impl.ContactSeviceImpl;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ContactController {

    @Autowired
    private ContactSeviceImpl contactSevice;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/contact")
    public ModelAndView contact() {

        ModelAndView modelAndView = new ModelAndView("user/contact");
        modelAndView.addObject("layout",categoryRepository.findAll());
        modelAndView.addObject("contact", new Contact());
        return modelAndView;
    }

    @Autowired
    private JavaMailSender mailSender;



    @PostMapping("/contact")
    public ModelAndView saveMessageContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("user/contact");
        modelAndView.addObject("contact", new Contact());
        modelAndView.addObject("layout",categoryRepository.findAll());
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        } else {
            contactSevice.save(contact);
            SimpleMailMessage crunchifyMsg = new SimpleMailMessage();
            crunchifyMsg.setFrom("2838d82328-8590c0@inbox.mailtrap.io");
            crunchifyMsg.setTo(contact.getEmail());
            crunchifyMsg.setText(contact.getText());
            mailSender.send(crunchifyMsg);
            return modelAndView;
        }
    }
}
