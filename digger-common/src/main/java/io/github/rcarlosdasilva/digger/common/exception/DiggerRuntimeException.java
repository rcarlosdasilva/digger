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

    public DiggerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiggerRuntimeException(Throwable cause) {
        super(cause);
    }

    protected DiggerRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
