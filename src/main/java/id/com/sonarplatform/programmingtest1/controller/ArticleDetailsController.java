package id.com.sonarplatform.programmingtest1.controller;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 9:54 PM
@Last Modified 5/13/2024 9:54 PM
Version 1.0
*/

import id.com.sonarplatform.programmingtest1.model.ArticleDetails;
import id.com.sonarplatform.programmingtest1.model.response.SimpleResponse;
import id.com.sonarplatform.programmingtest1.service.ArticleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class ArticleDetailsController {

    private final ArticleDetailsService articleDetailsService;

    @Autowired
    public ArticleDetailsController(ArticleDetailsService articleDetailsService) {
        this.articleDetailsService = articleDetailsService;
    }

    @GetMapping("/articles")
    public ResponseEntity<SimpleResponse<List<ArticleDetails>>> getAllArticles(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(articleDetailsService.getAll(page, size));
    }

    @GetMapping("/view")
    public ResponseEntity<SimpleResponse<ArticleDetails>> getArticleByUrl(@RequestParam(value = "url") String url) {
        SimpleResponse<ArticleDetails> body = new SimpleResponse<>();
        body.setData(articleDetailsService.getByUrl(url));
        return ResponseEntity.ok(body);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<SimpleResponse<ArticleDetails>> getArticleById(@PathVariable(value = "id") int id) {
        SimpleResponse<ArticleDetails> body = new SimpleResponse<>();
        body.setData(articleDetailsService.getById(id));
        return ResponseEntity.ok(body);
    }
}
