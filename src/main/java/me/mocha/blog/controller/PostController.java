package me.mocha.blog.controller;

import me.mocha.blog.exception.PostNotFoundException;
import me.mocha.blog.model.entity.Post;
import me.mocha.blog.model.entity.User;
import me.mocha.blog.model.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/{id}")
    public ModelAndView view(@PathVariable("id") long id, ModelAndView mav, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("post not found!", "/"));
        mav.addObject("user", user);
        mav.addObject("post", post);
        mav.setViewName("post");
        return mav;
    }

    @GetMapping("/editor")
    public ModelAndView editor(@RequestParam("id") Long id, ModelAndView mav) {

        mav.setViewName("editor");
        return mav;
    }

}
