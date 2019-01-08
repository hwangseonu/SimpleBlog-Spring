package me.mocha.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public ModelAndView index(ModelAndView mav, HttpSession session) {
        mav.setViewName("index");
        return mav;
    }

}
