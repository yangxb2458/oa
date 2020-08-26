package com.core136.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.mapper.email.EmailTagsMapper;

@Service
public class EmailTagsService{
@Autowired
private EmailTagsMapper emailTagsMapper;

}
