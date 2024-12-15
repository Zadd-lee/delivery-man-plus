package com.deliveryManPlus.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode{
    EXIST_USER(HttpStatus.CONFLICT,"이미 가입한 사용자입니다"),
    CANCELED_USER(HttpStatus.GONE,"탈퇴한 사용자입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
