package me.mocha.blog.controller;

import me.mocha.blog.exception.ApplicationException;
import me.mocha.blog.exception.PostNotFoundException;
import me.mocha.blog.exception.UnauthorizedException;
import me.mocha.blog.model.entity.Post;
import me.mocha.blog.model.entity.User;
import me.mocha.blog.model.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/editor")
public class EditorController {

    private final PostRepository postRepository;

    public EditorController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public ModelAndView view(@RequestParam(value = "id", required = false) Long id, ModelAndView mav, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = null;
        if (user == null) {
            throw new UnauthorizedException("Please login", "/login");
        }
        if (id != null) {
            post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Cannot find post by id", "/"));
            if (!post.getUser().equals(user)) {
                throw new ApplicationException("Permission denied", "This post is not for you.", "/", 403);
            }
        }
        mav.addObject("post", post);
        mav.setViewName("editor");
        return mav;
    }

}
