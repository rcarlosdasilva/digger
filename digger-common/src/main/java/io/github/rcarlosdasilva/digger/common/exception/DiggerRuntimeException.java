package io.github.rcarlosdasilva.digger.common.exception;

/**
 * Digger的运行时根异常
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class DiggerRuntimeException extends RuntimeException {

  public DiggerRuntimeException() {
    super();
  }

  public DiggerRuntimeException(String message) {
    super(message);
  }

  public DiggerRuntimeException(Throwable cause) {
    super(cause);
  }

  public DiggerRuntimeException(Throwable cause, String message) {
    super(message, cause);
  }

  protected DiggerRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
