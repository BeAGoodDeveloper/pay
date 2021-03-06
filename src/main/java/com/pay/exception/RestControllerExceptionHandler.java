package com.pay.exception;

import com.pay.domain.ErrorResponseVO;
import com.pay.errorEnum.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO unknownException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_9999);
    }

    @ExceptionHandler(value = {UnAuthorizedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO unAuthorizedException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_0001);
    }

    @ExceptionHandler(value = {TokenCreateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO tokenCreateException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_0002);
    }

    @ExceptionHandler(value = {NotFoundUserException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO notFoundUserException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_0003);
    }

    @ExceptionHandler(value = {NotFoundRoomException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO notFoundRoomException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_0004);
    }

    @ExceptionHandler(value = {NotParticipateRoomException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO notParticipateRoomException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_0005);
    }

    @ExceptionHandler(value = {ReceiveMoneyTimeOutException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO receiveMoneyTimeOutException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_0006);
    }

    @ExceptionHandler(value = {NotFoundSpreadListException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO notFoundSpreadListException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorCodeEnum.ERROR_0007);
    }

    private ErrorResponseVO getErrorResponse(ErrorCodeEnum errorCodeEnum) {
        return ErrorResponseVO.builder()
                .errorCode(errorCodeEnum.getErrorCode())
                .errorMessage(errorCodeEnum.getErrorMessage())
                .build();
    }
}