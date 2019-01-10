package me.mocha.blog.advice;

import me.mocha.blog.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ApplicationException.class)
    public ModelAndView notFoundHandler(ApplicationException e) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", e.getTitle());
        mav.addObject("message", e.getMessage());
        mav.addObject("after", e.getAfter());
        return mav;
    }

}
