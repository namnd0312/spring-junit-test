package com.namnd.junittestdemo.utils;

import com.namnd.junittestdemo.dto.UserDTO;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserValidator.class)
public @interface UserValidation {

    String message() default "Invalid value";

    Class<?>[] groups() default {};

    Class<UserDTO> payload();
}
