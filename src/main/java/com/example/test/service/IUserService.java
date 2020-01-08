package com.example.test.service;

import com.example.test.entity.User;

public interface IUserService {
    User getUserByName(String name);

    User loginUser(User user);

    User logoutUser(User user);

    User registerUser(User user);
}
