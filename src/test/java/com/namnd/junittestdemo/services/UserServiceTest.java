package com.namnd.junittestdemo.services;

import com.namnd.junittestdemo.configs.LogicException;
import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.dto.mapper.UserMapper;
import com.namnd.junittestdemo.models.User;
import com.namnd.junittestdemo.repositories.UserRepository;
import com.namnd.junittestdemo.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private  UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void givenExistedEmail_whenCreateNewUser_thenThrowException() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam")
                .build();

        BDDMockito.given(userRepository.existsByEmail(userDTO.getEmail())).willReturn(true);
        Assertions.assertThrows(LogicException.class, () -> {
            userService.saveUser(userDTO);
        });
    }

    @Test
    public void givenUserObject_whenCreateNewUser_thenReturnNewUser() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam")
                .build();

        User userExpected = userMapper.toEntity(userDTO);
        BDDMockito.given(userRepository.existsByEmail(userDTO.getEmail())).willReturn(false);
        BDDMockito.given(userRepository.save(userMapper.toEntity(userDTO))).willReturn(userExpected);
        User userActual = userService.saveUser(userDTO);
        Assertions.assertEquals(userExpected, userActual);
    }

    @Test
    public void whenFindUserByIdNotExisted_thenThrowException() throws LogicException {

        User user= User.builder()
                .id(1L)
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam")
                .build();

        BDDMockito.when(this.userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(LogicException.class, () -> this.userService.findById(1L));
    }


    @Test
    public  void whenFindUserById_thenReturnUser() throws LogicException {
        User user= User.builder()
                .id(1L)
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam User")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam UerDTO")
                .build();

        BDDMockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
        BDDMockito.when(this.userMapper.toDto(user)).thenReturn(userDTO);
        UserDTO actualResult = this.userService.findById(1L);
        Assertions.assertNotNull(actualResult);
    }
}
