package io.github.rcarlosdasilva.digger.common.exception;

/**
 * Digger的根异常
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class DiggerException extends Exception {

  public DiggerException() {
    super();
  }

  public DiggerException(String message) {
    super(message);
  }

  public DiggerException(Throwable cause) {
    super(cause);
  }

  public DiggerException(Throwable cause, String message) {
    super(message, cause);
  }

  protected DiggerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
