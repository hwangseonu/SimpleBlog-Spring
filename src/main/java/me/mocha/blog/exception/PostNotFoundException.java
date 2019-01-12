package me.mocha.blog.exception;

public class PostNotFoundException extends ApplicationException {

    public PostNotFoundException(String msg, String after) {
        super("404 Not Found", msg, after, 404);
    }

}
