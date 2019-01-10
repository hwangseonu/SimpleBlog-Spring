package me.mocha.blog.exception;

public class IncorrectPasswordException extends ApplicationException {

    public IncorrectPasswordException(String msg, String after){
        super("incorrect password!", msg, after, 401);
    }

}
