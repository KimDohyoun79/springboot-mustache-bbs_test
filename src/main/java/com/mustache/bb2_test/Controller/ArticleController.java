package com.mustache.bb2_test.Controller;

import com.mustache.bb2_test.domin.dto.ArticleDto;
import com.mustache.bb2_test.domin.entity.Article;
import com.mustache.bb2_test.domin.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @GetMapping("/new")
    public String firstPage(){
        return "new";
    }

    @PostMapping
    public String add(ArticleDto articleDto){
        log.info("add");
        Article article =articleRepository.save(articleDto.toEntity());
        log.info("generatedId:{}", article.getId());
        return "new";
    }

    @GetMapping(value = "/{id}")
    public String readSingle(@PathVariable Long id, Model model) {
        log.info("id :" + id);
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "show";
    }

    @GetMapping(value = "/list")
    public String readAll(Model model) {
        List<Article> articles= articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "list";
    }

}
