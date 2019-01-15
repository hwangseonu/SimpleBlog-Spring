package me.mocha.blog.controller;

import me.mocha.blog.exception.ApplicationException;
import me.mocha.blog.exception.ForbiddenException;
import me.mocha.blog.exception.PostNotFoundException;
import me.mocha.blog.exception.UnauthorizedException;
import me.mocha.blog.model.entity.Post;
import me.mocha.blog.model.entity.User;
import me.mocha.blog.model.repository.PostRepository;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
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

        post.addViews(1);
        postRepository.save(post);

        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String parsedContent = renderer.render(parser.parse(post.getContent()));
        post.setContent(parsedContent);
        mav.addObject("user", user);
        mav.addObject("post", post);
        mav.addObject("isOwner", post.getUser().equals(user));
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
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("cannot find post by id", "/"));
        post.setTitle(title);
        post.setContent(content);
        post = postRepository.save(post);
        if (post == null) {
            throw new ApplicationException("Server error", "cannot save post", "/", 500);
        }
        return "redirect:/post/"+post.getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("cannot find post by id", "/"));

        if (!post.getUser().equals(user)) {
            throw new ForbiddenException("This post is not your own", "/");
        }
        postRepository.delete(post);
        return "redirect:/";
    }

}
