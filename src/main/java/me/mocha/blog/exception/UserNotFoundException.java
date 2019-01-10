package me.mocha.blog.exception;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String msg, String after) {
        super("404 Not Found", msg, after, 404);
    }

}
