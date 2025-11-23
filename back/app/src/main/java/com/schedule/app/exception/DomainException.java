package com.schedule.app.exception;

import com.schedule.app.enums.DomainError;

public class DomainException extends RuntimeException {
  private final DomainError error;

  public DomainException(DomainError error, String message) {
    super(message);
    this.error = error;
  }

  public DomainError getError() {
    return error;
  }
}
