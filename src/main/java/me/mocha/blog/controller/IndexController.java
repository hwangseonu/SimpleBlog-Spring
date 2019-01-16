package me.mocha.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.blog.model.entity.Post;
import me.mocha.blog.model.entity.User;
import me.mocha.blog.model.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class IndexController {

    private final PostRepository postRepository;

    public IndexController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav, HttpSession session,
                              @PageableDefault(size = 5, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        User user = (User) session.getAttribute("user");
        Page<Post> posts = postRepository.findAll(pageable);
        mav.addObject("posts", posts);
        mav.addObject("user", user);
        mav.setViewName("index");
        return mav;
    }

}
