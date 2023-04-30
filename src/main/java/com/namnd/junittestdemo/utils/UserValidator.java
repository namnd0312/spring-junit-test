package com.namnd.junittestdemo.utils;

import com.namnd.junittestdemo.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<UserValidation, UserDTO> {


@Override
public boolean isValid(UserDTO value, ConstraintValidatorContext context) {

    if ("PERSONAL".equals(value.getTypeOfUser())) {

        if (value.getAddress() == null) {
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate("myCustomValidat")
                    .addConstraintViolation();
            return false;
        }


    }

    return true;
}
}