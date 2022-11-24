package io.younghwang.springframeworkbasic.user.exception;

public class SqlUpdateFailureException extends RuntimeException {
    public SqlUpdateFailureException(String message) {
        super(message);
    }
}
