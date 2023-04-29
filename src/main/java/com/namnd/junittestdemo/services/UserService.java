package com.namnd.junittestdemo.services;

import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.models.User;

public interface UserService {

    User saveUser(UserDTO userDto) throws Exception;
}
