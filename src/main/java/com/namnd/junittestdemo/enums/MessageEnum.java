package com.namnd.junittestdemo.enums;

public enum MessageEnum {

    SUCCESS("00", "Success"),
    AUTHEN_TYPE_NOT_FOUND("002", "Authen type not found"),
    INTERNAL_API_ERROR("500", "INTERNAL SERVER ERROR"),
    INTERNAL_API_T24_ERROR("006", "INTERNAL SERVER T24 ERROR"),
    CONNECT_T24_ERROR("003", "CONNECT TO CORE T24 ERROR"),
    BAD_REQUEST("400", "BAD REQUEST"),
    REJECTED_IF_STATUS_NOT_PENDING("005", "You only can execute with record's status is pending"),
    RECORD_NOT_EXISTED("004", "Record is not existed"),
    FIELD_INVALID("010", "FIELD_INVALID"),
    //    ACTION_T24_INVALID("010", "actionT24 invalid"),
    LIST_EMPTY("011", "EMPTY"),
    REJECT_DELETE_RECORD_CREATED_BY_COMBO("012", "YOU CAN NOT DELETE RECORD CREATED BY COMBO"),
    OK("00", "OK"),

    PARAMETER_IN_VALID("01", "Parameter is invalid: %s"),
    UN_AUTHORIZE("02", "UN AUTHORIZER"),
    USER_TOKEN_INVALID("03", "USER_TOKEN_INVALID"),
    USER_MISSING_ROLE("04", "USER_MISSING_ROLE"),
    ACCESS_DENIED("05", "Access is denied"),

    EMAIL_ALREADY_IN_USE("06", "Email is already in use");

    private final String code;
    private final String message;

    MessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
