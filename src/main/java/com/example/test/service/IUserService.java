package com.example.test.service;

import com.example.test.entity.User;

public interface IUserService {
    User getUserById(int id);

    User loginUser(User user);

    User logoutUser(User user);
}
