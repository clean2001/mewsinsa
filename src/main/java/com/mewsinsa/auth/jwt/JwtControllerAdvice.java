package com.mewsinsa.auth.jwt;

import com.mewsinsa.auth.jwt.exception.NoTokenException;
import com.mewsinsa.auth.jwt.exception.NonExistentMemberException;
import com.mewsinsa.coupon.exception.FailToIssueCouponException;
import com.mewsinsa.global.response.DetailedStatus;
import com.mewsinsa.global.response.FailureResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtControllerAdvice {

  @ExceptionHandler(NoTokenException.class)
  protected ResponseEntity<FailureResult> handleNoTokenException(
      NoTokenException e) {

    final FailureResult result = new FailureResult.Builder()
        .status(DetailedStatus.NO_TOKEN)
        .code(DetailedStatus.NO_TOKEN.getCode())
        .message(e.getMessage())
        .build();

    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NonExistentMemberException.class)
  protected ResponseEntity<FailureResult> handleNonExistentMemberException(
      NonExistentMemberException e) {

    final FailureResult result = new FailureResult.Builder()
        .status(DetailedStatus.NO_TOKEN)
        .code(DetailedStatus.NO_TOKEN.getCode())
        .message(e.getMessage())
        .build();

    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }

}
