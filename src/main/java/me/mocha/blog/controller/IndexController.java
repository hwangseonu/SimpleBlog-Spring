package me.mocha.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.blog.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@Slf4j
public class IndexController {

    @RequestMapping
    public ModelAndView index(ModelAndView mav, HttpSession session) {
        User user = (User) session.getAttribute("user");
        mav.addObject("user", user);
        mav.setViewName("index");
        return mav;
    }

}
