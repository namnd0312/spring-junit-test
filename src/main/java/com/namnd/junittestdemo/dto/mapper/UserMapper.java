package com.namnd.junittestdemo.dto.mapper;

import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto){

        User entity = new User();
        BeanUtils.copyProperties(dto, entity);

        return entity;
    }

    public UserDTO toDto(User entity){
        UserDTO dto = new UserDTO();

        BeanUtils.copyProperties(entity, dto);

        return dto;
    }
}
