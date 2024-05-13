package id.com.sonarplatform.programmingtest1.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 11:20 AM
@Last Modified 5/13/2024 11:20 AM
Version 1.0
*/

import id.com.sonarplatform.programmingtest1.model.ArticleDetails;
import id.com.sonarplatform.programmingtest1.service.FetchRssService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FetchRssServiceImpl implements FetchRssService {


    @Override
    public List<ArticleDetails> fetchArticleDetailsFromRss(String sourceUrl) throws IOException {
        Document doc = Jsoup.connect(sourceUrl).get();
        Elements items = doc.select("item");
        return items.parallelStream().map(FetchRssServiceImpl::mapToArticleDetails).toList();
    }


    static ArticleDetails mapToArticleDetails(Element item) {
        ArticleDetails articleDetails = new ArticleDetails();
        articleDetails.setUrl(item.select("link").text());
        articleDetails.setTitle(item.select("title").text());
        Document contents = null;
        try {
            contents = Jsoup.connect(articleDetails.getUrl()).get();
        } catch (IOException e) {
            log.error("[ERROR][URL: {}] {}", articleDetails.getUrl(), e.getMessage(), e);
        }

        // Get Content
        Element content = Optional.ofNullable(contents).map(doc -> doc.selectFirst(".post-content")).orElse(null);
        articleDetails.setContent(Optional.ofNullable(content).map(Element::text).orElse(null));

        // Set publication time to timestamp
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

        try {
            articleDetails.setPubDate(format.parse(item.select("pubDate").text()));
            articleDetails.setPublicationTime(format.parse(item.select("pubDate").text()).getTime());
        } catch (ParseException e) {
            log.error("{}", e.getMessage(), e);
        }
        return articleDetails;
    }
}
