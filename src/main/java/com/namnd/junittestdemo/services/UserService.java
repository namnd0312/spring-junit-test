package com.namnd.junittestdemo.services;

import com.namnd.junittestdemo.configs.LogicException;
import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.models.User;

import java.util.List;

public interface UserService {

    User saveUser(UserDTO userDto) throws Exception;

    UserDTO findById(Long id) throws LogicException;
    List<UserDTO> findAll();
}
