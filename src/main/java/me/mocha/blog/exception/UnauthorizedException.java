package me.mocha.blog.exception;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException(String msg, String after) {
        super("Unauthorized", msg, after, 401);
    }

}
