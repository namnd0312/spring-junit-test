package com.namnd.junittestdemo.services;

import com.namnd.junittestdemo.configs.LogicException;
import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.dto.mapper.UserMapper;
import com.namnd.junittestdemo.models.User;
import com.namnd.junittestdemo.repositories.UserRepository;
import com.namnd.junittestdemo.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private  UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void init(){
        user = User.builder()
                .id(1L)
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam")
                .build();

        userDTO = UserDTO.builder()
                .id(1L)
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam")
                .build();
    }

    @DisplayName("Test case when create a new User with duplicate email in data base then throw Exception")
    @Test
    public void givenExistedEmail_whenCreateNewUser_thenThrowException() throws Exception {
        given(userRepository.existsByEmail(userDTO.getEmail())).willReturn(true);
        Assertions.assertThrows(LogicException.class, () -> {
            userService.saveUser(userDTO);
        });
    }

    @Test
    public void givenUserObject_whenCreateNewUser_thenReturnNewUser() throws Exception {
        User userExpected = userMapper.toEntity(userDTO);
        given(userRepository.existsByEmail(userDTO.getEmail())).willReturn(false);
        given(userRepository.save(userMapper.toEntity(userDTO))).willReturn(userExpected);
        User userActual = userService.saveUser(userDTO);
        Assertions.assertEquals(userExpected, userActual);
    }

    @Test
    public void whenFindUserByIdNotExisted_thenThrowException() throws LogicException {

        BDDMockito.when(this.userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(LogicException.class, () -> this.userService.findById(1L));
    }


    @Test
    public  void whenFindUserById_thenReturnUserDTOObject() throws LogicException {
        BDDMockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(user));
        BDDMockito.when(this.userMapper.toDto(user)).thenReturn(userDTO);
        UserDTO actualResult = this.userService.findById(1L);
        Assertions.assertNotNull(actualResult);
    }

    @Test
    public void givenUserList_then_findAll_returnUserList(){
        User user2 = User.builder()
                .id(2L)
                .email("namnd0312@gmail.com")
                .typeOfUser("Personal")
                .address("Hanoi")
                .age(19)
                .name("Nghiem Duc Nam")
                .build();

        List<User> users = List.of(user, user2);

        given(this.userRepository.findAll()).willReturn(users);

        List<UserDTO> listUser = this.userService.findAll();

        Assertions.assertNotNull(listUser);
        Assertions.assertEquals(2, listUser.size());
    }

    @Test
    public void givenUserList_then_findAll_returnUserListEmpty(){
        given(this.userRepository.findAll()).willReturn(Collections.emptyList());
        List<UserDTO> listUser = this.userService.findAll();
        Assertions.assertEquals(0, listUser.size());
    }

    @Test void givenUserDTO_thenUpdateUser_returnUserObject() throws Exception {
        given(this.userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        user.setName("update name");
        user.setAge(100);

        given(userMapper.toEntity(userDTO)).willReturn(user);
        User userActual = this.userService.updateUser(userDTO);

        Assertions.assertEquals("update name", userActual.getName());
        Assertions.assertEquals(100, userActual.getAge());
    }
}