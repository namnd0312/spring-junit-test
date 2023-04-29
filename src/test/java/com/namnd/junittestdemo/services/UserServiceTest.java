package com.namnd.junittestdemo.services;

import com.namnd.junittestdemo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    void whenGetIdNotExistedInDatabase_shouldThrowException(){
        Long invalidUserId = 100L;


    }

}
