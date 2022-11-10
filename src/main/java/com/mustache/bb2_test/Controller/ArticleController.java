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
import java.util.Optional;

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

    @GetMapping(value = "/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(!optionalArticle.isEmpty()){
            model.addAttribute("article", optionalArticle.get());
            return "edit";
        }
        else{
            model.addAttribute("massage", String.format("%d가 없습니다.", id));
            return "error";
        }
    }
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, ArticleDto articleDto, Model model) {
        log.info("title:{} content:{}", articleDto.getTitle(), articleDto.getContent());
        Article article = articleRepository.save(articleDto.toEntity());
        model.addAttribute("article", article);
        return String.format("redirect:/articles/%d", article.getId());
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return "redirect:/articles/list";
    }

}
