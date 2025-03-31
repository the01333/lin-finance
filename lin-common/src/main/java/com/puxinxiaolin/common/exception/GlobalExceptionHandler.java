package com.puxinxiaolin.common.exception;

import com.puxinxiaolin.common.constant.ApiResponseCode;
import com.puxinxiaolin.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 全局异常处理器
 * @Author: YCcLin
 * @Date: 2025/3/31 20:54
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings("NullableProblems")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 全局异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handlerException(Exception exception) {
        log.info("GlobalExceptionHandler.handlerException.exception:{}", exception.getMessage(), exception);

        ApiResponse<Object> response = new ApiResponse<>();
        Map<String, String> errors = new HashMap<>();
        errors.put(ApiResponseCode.SERVICE_ERROR.getMessage(), exception.getMessage());
        response.error(ApiResponseCode.SERVICE_ERROR.getCode(), errors);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 业务处理异常
     *
     * @param parameterException
     * @return
     */
    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<ApiResponse<Object>> apiErrorException(ParameterException parameterException) {
        log.info("GlobalExceptionHandler.apiErrorException.parameterException:{}",
                parameterException.getMessage(), parameterException);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setCodeMessage(parameterException.getMessage());
        apiResponse.error(parameterException.getCode(), parameterException.getFieldErrors());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * 业务处理异常
     *
     * @param bizException
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiResponse<Object>> apiErrorException(BizException bizException) {
        log.info("GlobalExceptionHandler.apiErrorException.bizException:{}", bizException.getMessage(), bizException);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        Map<String, String> errors = new HashMap<>();
        errors.put(ApiResponseCode.BUSINESS_ERROR.getMessage(), bizException.getMessage());
        apiResponse.error(bizException.getCode(), errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 业务处理异常
     *
     * @param bizException
     * @return
     */
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ApiResponse<Object>> apiErrorException(LoginException bizException) {
        log.info("GlobalExceptionHandler.apiErrorException.bizException:{}", bizException.getMessage(), bizException);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        Map<String, String> errors = new HashMap<>();
        errors.put(ApiResponseCode.LOGIN_ERROR.getMessage(), bizException.getMessage());
        apiResponse.error(bizException.getCode(), errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * BindException 异常处理
     *
     * @param bindException
     * @return
     * @tips: <p>
     * 1）作用于 @Validated @Valid 注解
     * 2）仅对于表单提交参数进行异常处理, 对于以 Json 格式提交将会失效
     * 3）只对实体参数进行校验
     * 注: Controller 里的方法必须加上 @Validated 注解
     * </p>
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException bindException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("GlobalExceptionHandler.apiErrorException.bindException:{}", bindException.getMessage(), bindException);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        Map<String, String> errors = new HashMap<>();
        bindException.getFieldErrors().forEach(p -> {
            errors.put(p.getField(), p.getDefaultMessage());
        });
        apiResponse.error(ApiResponseCode.PARAMETER_INVALID.getCode(), errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * MethodArgumentNotValidException - Spring封装的参数验证异常处理
     *
     * @param methodArgumentNotValidException
     * @return
     * @tips: <p>
     * 1）作用于 @Validated @Valid 注解
     * 2）接收参数加上@RequestBody注解（json格式）的异常处理
     * </p>
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleMethodArgumentNotValid.methodArgumentNotValidException:", methodArgumentNotValidException);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getFieldErrors().forEach(p -> {
            errors.put(p.getField(), p.getDefaultMessage());
        });
        apiResponse.error(ApiResponseCode.PARAMETER_INVALID.getCode(), errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * ConstraintViolationException - jsr规范中的验证异常，嵌套检验问题
     *
     * @param constraintViolationException
     * @return
     * @tips: <p>
     * 1）作用于 @NotBlank @NotNull @NotEmpty 注解, 校验单个 String、Integer、Collection等参数异常处理
     * 注: Controller类上必须添加 @Validated 注解, 不是加在 Controller类的方法上
     * 否则接口单个参数校验无效（RequestParam，PathVariable参数校验）
     * </p>
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationExceptionHandler(ConstraintViolationException constraintViolationException) {
        log.error("GlobalExceptionHandler.constraintViolationExceptionHandler.constraintViolationException:", constraintViolationException);

        ApiResponse<Object> apiResponse = new ApiResponse<>();
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        Map<String, String> errors = new HashMap<>();
        violations.forEach(p -> {
            String fieldName = null;
            //获取字段名称（最后一个元素才是）
            Iterator<Path.Node> nodeIterator = p.getPropertyPath().iterator();
            while (nodeIterator.hasNext()) {
                fieldName = nodeIterator.next().getName();
            }
            errors.put(fieldName, p.getMessage());
        });
        apiResponse.error(ApiResponseCode.PARAMETER_INVALID.getCode(), errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
