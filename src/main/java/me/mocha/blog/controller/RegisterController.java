package me.mocha.blog.controller;

import me.mocha.blog.exception.ApplicationException;
import me.mocha.blog.exception.IncorrectPasswordException;
import me.mocha.blog.exception.UserConflictException;
import me.mocha.blog.exception.UserNotFoundException;
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
@RequestMapping("/register")
public class RegisterController {

    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView view(ModelAndView mav) {
        mav.setViewName("register");
        return mav;
    }

    @PostMapping
    public String register(@RequestParam("username") String username, @RequestParam("nickname") String nickname, @RequestParam("password") String password, HttpSession session) {
        if (userRepository.existsById(username) || userRepository.existsByNickname(nickname)) {
            throw new UserConflictException("이미 존재하는 사용자", "/register");
        }

        User user = userRepository.save(User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .build());
        if (user == null) {
            throw new ApplicationException("Server error!", "cannot save user data", "/register", 500);
        }
        session.setAttribute("user", user);
        return "redirect:/";
    }

}
