package com.example.test.service.impl;

import com.example.test.entity.User;
import com.example.test.mapper.UserMapper;
import com.example.test.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ITestService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int id){
        return userMapper.selectByPrimaryKey(id);
    }
}
