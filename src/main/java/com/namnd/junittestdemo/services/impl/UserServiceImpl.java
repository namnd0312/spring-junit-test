package com.namnd.junittestdemo.services.impl;

import com.namnd.junittestdemo.configs.LogicException;
import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.dto.mapper.UserMapper;
import com.namnd.junittestdemo.models.User;
import com.namnd.junittestdemo.repositories.UserRepository;
import com.namnd.junittestdemo.services.UserService;
import org.springframework.stereotype.Service;

import static com.namnd.junittestdemo.enums.MessageEnum.EMAIL_ALREADY_IN_USE;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User saveUser(UserDTO userDto) throws Exception {
        boolean existsByEmail = this.userRepository.existsByEmail(userDto.getEmail());
        if(existsByEmail){
            throw new LogicException(EMAIL_ALREADY_IN_USE);
        }

        User user = this.userMapper.toEntity(userDto);

        return this.userRepository.save(user);
    }
}
