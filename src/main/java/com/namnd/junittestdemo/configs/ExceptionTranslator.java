package com.namnd.junittestdemo.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namnd.junittestdemo.dto.payloads.responses.Response;
import com.namnd.junittestdemo.enums.MessageEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.namnd.junittestdemo.enums.MessageEnum.ACCESS_DENIED;
import static com.namnd.junittestdemo.enums.MessageEnum.PARAMETER_IN_VALID;

@ControllerAdvice
public class ExceptionTranslator extends RequestBodyAdviceAdapter {

    private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);
    private HttpServletRequest request;

    public ExceptionTranslator(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!request.getRequestURI().contains("medias")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                log.info("[URI: {}][REQUEST_BODY]: {}",
                        this.request.getRequestURI(), mapper.writeValueAsString(body));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    /**
     Handle exception for validate input data field and custom validation;
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getObjectName(), e.getObjectName() + " " + e.getDefaultMessage()));
        String message = errors.values().stream().collect(Collectors.toList()).stream().collect(Collectors.joining("#"));
        return new ResponseEntity<>(Response.error(PARAMETER_IN_VALID, message), HttpStatus.BAD_REQUEST);
    }

    /**
     Handle common exception all system;
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> processRuntimeException(Exception ex) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            log.info("[URI: {}][NG][EXCEPTION]: {}",
                    this.request.getRequestURI(), mapper.writeValueAsString(ex.getMessage()));
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new ResponseEntity<>(Response.error(MessageEnum.INTERNAL_API_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> inputException(AccessDeniedException ex) {
        return new ResponseEntity<>(Response.error(ACCESS_DENIED), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LogicException.class)
    public ResponseEntity<?> logicException(LogicException ex) {
        return new ResponseEntity<>(Response.error(ex.getMessageEnum()), HttpStatus.OK);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<Object> sqlError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some SQL exception occured");
    }
}
