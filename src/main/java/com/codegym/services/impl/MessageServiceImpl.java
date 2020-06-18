package com.codegym.services.impl;

import com.codegym.model.Message;
import com.codegym.repository.MessageRepository;
import com.codegym.services.BaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements BaseServices<Message> {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(long id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public void save(Message object) {
        messageRepository.save(object);
    }

    @Override
    public void remove(long id) {
        messageRepository.deleteById(id);
    }
}
