package com.example.test.service.impl;

import com.example.test.bean.RespBean;
import com.example.test.entity.User;
import com.example.test.mapper.UserMapper;
import com.example.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User loginUser(User user) {

        User res = userMapper.selectByUsername(user.getUsername());
        System.out.println(res);
        if(res != null && res.getLogin()!=1){
            res.setLogin(1);
            userMapper.updateByPrimaryKey(res);
            System.out.println(res);
            return res;
        }else{
            return null;
        }
    }
}
