package com.namnd.junittestdemo.dto;

import com.namnd.junittestdemo.utils.UserValidation;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty
    private String name;


    @NotNull
    private Integer age;

    @NotEmpty
    @ApiModelProperty(notes = "Email của User")
    private String email;

    @ApiModelProperty(notes = "Loại của User hệ thống")
    @NotEmpty
    private String typeOfUser;


    @ApiModelProperty(notes = "Địa chỉ của User")
    private String address;
}
