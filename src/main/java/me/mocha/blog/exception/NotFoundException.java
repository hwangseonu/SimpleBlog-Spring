package me.mocha.blog.exception;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String msg, String after) {
        super(msg, after, 404);
    }

}
