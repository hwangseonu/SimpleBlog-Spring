package me.mocha.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.mocha.blog.model.entity.Post;
import me.mocha.blog.model.entity.User;
import me.mocha.blog.model.repository.PostRepository;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    public ModelAndView lists(ModelAndView mav, HttpSession session,
                              @PageableDefault(size = 5, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        User user = (User) session.getAttribute("user");
        Page<Post> posts = postRepository.findAll(pageable);
        posts.getContent().forEach(p -> {
            Parser parser = Parser.builder().build();
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String parsedContent = renderer.render(parser.parse(p.getContent()));
            Document document = Jsoup.parse(parsedContent);
            p.setContent(document.text());
        });
        mav.addObject("posts", posts);
        mav.addObject("user", user);
        mav.setViewName("index");
        return mav;
    }

}
