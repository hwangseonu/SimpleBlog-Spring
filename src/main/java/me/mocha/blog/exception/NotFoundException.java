package me.mocha.blog.exception;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String msg) {
        super(msg, 404);
    }

}
