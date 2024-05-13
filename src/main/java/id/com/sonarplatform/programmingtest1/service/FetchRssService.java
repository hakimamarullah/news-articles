package id.com.sonarplatform.programmingtest1.service;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 11:19 AM
@Last Modified 5/13/2024 11:19 AM
Version 1.0
*/

import id.com.sonarplatform.programmingtest1.model.ArticleDetails;

import java.io.IOException;
import java.util.List;

public interface FetchRssService {

    List<ArticleDetails> fetchArticleDetailsFromRss(String sourceUrl) throws IOException;
}
