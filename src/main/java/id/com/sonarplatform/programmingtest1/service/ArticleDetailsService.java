package id.com.sonarplatform.programmingtest1.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 9:55 PM
@Last Modified 5/13/2024 9:55 PM
Version 1.0
*/

import id.com.sonarplatform.programmingtest1.model.ArticleDetails;

import java.io.IOException;
import java.util.List;

public interface ArticleDetailsService {

    List<ArticleDetails> getAll(int page, int size);

    ArticleDetails getByUrl(String url);

    ArticleDetails getById(int id);

    List<ArticleDetails> fetchFromIndex(String url) throws IOException;
}
