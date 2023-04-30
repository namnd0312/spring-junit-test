package com.namnd.junittestdemo.controllers;


import com.namnd.junittestdemo.dto.UserDTO;
import com.namnd.junittestdemo.dto.payloads.responses.Response;
import com.namnd.junittestdemo.dto.payloads.responses.ResponseBody;
import com.namnd.junittestdemo.models.User;
import com.namnd.junittestdemo.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Tạo mới User", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 401, message = "Chưa xác thực"),
            @ApiResponse(code = 403, message = "Truy cập bị cấm"),
            @ApiResponse(code = 404, message = "Không tìm thấy")
    })
    @PostMapping
    ResponseEntity<ResponseBody<?>> createUser(@Valid @RequestBody UserDTO requestDto) throws Exception {
        User user = this.userService.saveUser(requestDto);
        return new ResponseEntity<>(Response.ok(user), HttpStatus.OK);
    }
}
