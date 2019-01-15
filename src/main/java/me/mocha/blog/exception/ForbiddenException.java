package me.mocha.blog.exception;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException(String msg, String after) {
        super("403 Forbidden", msg, after, 403);
    }

}
