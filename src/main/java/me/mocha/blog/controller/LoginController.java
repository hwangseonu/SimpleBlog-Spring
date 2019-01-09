package me.mocha.blog.controller;


import me.mocha.blog.exception.NotFoundException;
import me.mocha.blog.model.entity.User;
import me.mocha.blog.model.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

    @PostMapping
    public String signIn(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        User user = userRepository.findById(username).orElse(null);

        if (user == null) {
            throw new NotFoundException("Cannot find user.", "/login");
        }

        return "redirect:/";
    }

}
