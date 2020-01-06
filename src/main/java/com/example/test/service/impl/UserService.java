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
        if(res != null && res.getLogin()!=1 && res.getPassword().equals(user.getPassword())){
            res.setLogin(1);
            userMapper.updateByPrimaryKey(res);
            return res;
        }else{
            return null;
        }
    }

    @Override
    public User logoutUser(User user) {
        User res = userMapper.selectByUsername(user.getUsername());
        if(res != null && res.getLogin()!=0 && res.getPassword().equals(user.getPassword())){
            res.setLogin(0);
            userMapper.updateByPrimaryKey(res);
            return res;
        }else{
            return null;
        }
    }
}
