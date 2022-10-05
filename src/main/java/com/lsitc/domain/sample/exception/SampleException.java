package com.lsitc.domain.sample.exception;

import com.lsitc.global.error.exception.BusinessException;
import com.lsitc.global.error.exception.ErrorCode;

public class SampleException extends BusinessException {

  public SampleException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public SampleException(ErrorCode errorCode) {
    super(errorCode);
  }
}