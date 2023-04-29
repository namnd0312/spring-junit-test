package com.namnd.junittestdemo.dto.payloads.responses;

import com.namnd.junittestdemo.enums.MessageEnum;

import static com.namnd.junittestdemo.utils.Constant.*;

public class Response<T> {

    public static ResponseBody<?> ok(Object data) {
        return new ResponseBody<>("00", OK, SUCCESS, data);
    }

    public static ResponseBody<?> ok() {
        return new ResponseBody<>("00", OK, SUCCESS, null);
    }

    public static ResponseBody<?> error(MessageEnum messageEnum, Object data) {
        return new ResponseBody<>(messageEnum.getCode(), FAIL, messageEnum.getMessage(), data);
    }

    public static ResponseBody<?> error(MessageEnum messageEnum, String message) {

        return ResponseBody.builder()
                .code(messageEnum.getCode())
                .message(String.format(messageEnum.getMessage(), message))
                .status(FAIL)
                .build();
    }

    public static ResponseBody<?> error(MessageEnum messageEnum) {
        return ResponseBody.builder()
                .code(messageEnum.getCode())
                .message(messageEnum.getMessage())
                .status(FAIL)
                .build();
    }
}
