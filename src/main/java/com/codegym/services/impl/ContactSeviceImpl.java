package com.codegym.services.impl;

import com.codegym.model.Contact;
import com.codegym.repository.ContactRepository;
import com.codegym.services.BaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Properties;

@Service
@Transactional
public class ContactSeviceImpl implements BaseServices<Contact> {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public void save(Contact object) {
            contactRepository.save(object);
    }

    @Override
    public Contact findById(long id) {
        return contactRepository.findById(id).get();
    }

    @Override
    public void remove(long id) {
        contactRepository.deleteById(id);
    }
}
