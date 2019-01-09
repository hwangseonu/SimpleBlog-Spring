package me.mocha.blog.exception;

import lombok.Getter;

public class ApplicationException extends RuntimeException {

    @Getter
    private int status;

    @Getter
    private String after;

    public ApplicationException(String msg, String after, int status) {
        super(msg);
        this.after = after;
        this.status = status;
    }

}
