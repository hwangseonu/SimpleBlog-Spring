package me.mocha.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @GetMapping
    public ModelAndView view(ModelAndView mav) {
        mav.setViewName("register");
        return mav;
    }

    @PostMapping
    public String register() {
        return "redirect:/";
    }

}
