package me.mocha.blog.exception;

import lombok.Getter;

public class ApplicationException extends RuntimeException {

    @Getter
    private int status;

    @Getter
    private String title;

    @Getter
    private String after;

    public ApplicationException(String title, String msg, String after, int status) {
        super(msg);
        this.title = title;
        this.after = after;
        this.status = status;
    }

}
