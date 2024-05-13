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
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FetchRssServiceImpl implements FetchRssService {
    @Override
    public void fetchRss(String sourceUrl) throws IOException {
        Document doc = Jsoup.connect(sourceUrl).get();
        Elements items = doc.select("item");
        items.parallelStream().map(FetchRssServiceImpl::mapToArticleDetails).collect(Collectors.toList()).forEach(System.out::println);

    }

    @PostConstruct
    void startUp() {
        try {
            fetchRss("https://jambi.antaranews.com/rss/terkini.xml");
        } catch (IOException e) {
           log.error("{}", e.getMessage(), e);
        }
    }

    static ArticleDetails mapToArticleDetails(Element item) {
        ArticleDetails articleDetails = new ArticleDetails();
        articleDetails.setUrl(item.select("link").text());
        articleDetails.setTitle(item.select("title").text());
        Document contents = null;
        try {
            contents = Jsoup.connect(articleDetails.getUrl()).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        articleDetails.setContent(contents.selectFirst(".post-content").text());
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

        try {
            articleDetails.setPublicationTime(format.parse(item.select("pubDate").text()).getTime());
        } catch (ParseException e) {
            log.error("{}", e.getMessage(), e);
        }
        return articleDetails;
    }
}
