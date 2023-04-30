package com.namnd.junittestdemo.services.impl;

import com.namnd.junittestdemo.configs.LogicException;
import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.dto.mapper.UserMapper;
import com.namnd.junittestdemo.models.User;
import com.namnd.junittestdemo.repositories.UserRepository;
import com.namnd.junittestdemo.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.namnd.junittestdemo.enums.MessageEnum.EMAIL_ALREADY_IN_USE;
import static com.namnd.junittestdemo.enums.MessageEnum.RECORD_NOT_EXISTED;

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

    @Override
    public User updateUser(UserDTO userDto) throws Exception {
        Optional<User> user = this.userRepository.findById(userDto.getId());

        if(user.isEmpty()){
            throw new LogicException(RECORD_NOT_EXISTED);
        }

        User entity = this.userMapper.toEntity(userDto);

        return this.userRepository.save(entity);
    }

    @Override
    public UserDTO findById(Long id) throws LogicException {

        Optional<User> user = this.userRepository.findById(id);

        if(user.isEmpty()){
            throw new LogicException(RECORD_NOT_EXISTED);
        }
        return userMapper.toDto(user.get());
    }

    @Override
    public List<UserDTO> findAll() {
        return this.userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
