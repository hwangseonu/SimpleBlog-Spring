package me.mocha.blog.controller;

import me.mocha.blog.exception.ApplicationException;
import me.mocha.blog.exception.PostNotFoundException;
import me.mocha.blog.exception.UnauthorizedException;
import me.mocha.blog.model.entity.Post;
import me.mocha.blog.model.entity.User;
import me.mocha.blog.model.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public String post(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new UnauthorizedException("Please login", "/login");
        }
        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .views(0)
                .build());
        if (post == null) {
            throw new ApplicationException("Server error", "cannot save post", "/", 500);
        }
        return "redirect:/post/"+post.getId();
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new UnauthorizedException("Please login", "/login");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found", "cannot find post by id"));
        post.setTitle(title);
        post.setContent(content);
        post = postRepository.save(post);
        if (post == null) {
            throw new ApplicationException("Server error", "cannot save post", "/", 500);
        }
        return "redirect:/post/"+post.getId();
    }

}
