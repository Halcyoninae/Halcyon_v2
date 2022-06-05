package com.jackmeng.audio.errors;

public class NoLocaleMediaException extends Exception {
  public NoLocaleMediaException(String message) {
    super(message);
  }

  public NoLocaleMediaException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoLocaleMediaException(Throwable cause) {
    super(cause);
  }

  public NoLocaleMediaException() {
  }

  @Override
  public void printStackTrace() {
    printStackTrace(System.err);
  }
}
