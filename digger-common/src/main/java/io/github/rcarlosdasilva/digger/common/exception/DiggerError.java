package io.github.rcarlosdasilva.digger.common.exception;

/**
 * Digger的根错误
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class DiggerError extends Error {

    public DiggerError() {
        super();
    }

    public DiggerError(String message) {
        super(message);
    }

    public DiggerError(String message, Throwable cause) {
        super(message, cause);
    }

    public DiggerError(Throwable cause) {
        super(cause);
    }

    protected DiggerError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
