package com.namnd.junittestdemo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotEmpty
    private String name;


    @NotNull
    private Integer age;

    @NotEmpty
    private String email;
}
