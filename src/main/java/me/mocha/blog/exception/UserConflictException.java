package me.mocha.blog.exception;

public class UserConflictException extends ApplicationException {

    public UserConflictException(String msg, String after) {
        super("User Conflict!", msg, after, 409);
    }

}
