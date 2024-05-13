package id.com.sonarplatform.programmingtest1;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 9:18 PM
@Last Modified 5/13/2024 9:18 PM
Version 1.0
*/

import id.com.sonarplatform.programmingtest1.model.ArticleDetails;
import id.com.sonarplatform.programmingtest1.repository.ArticleDetailMapper;
import id.com.sonarplatform.programmingtest1.service.ArticleDetailsService;
import id.com.sonarplatform.programmingtest1.service.FetchRssService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class DataSeeder {

    private final FetchRssService rssService;

    private final ArticleDetailsService articleDetailsService;

    private final ArticleDetailMapper articleDetailMapper;

    @Value("${source.rss.url}")
    private String rssUrl;

    @Value("${source.rss.enabled}")
    private boolean enableRss;

    @Value("${source.index.enabled}")
    private boolean enableIndex;

    @Autowired
    public DataSeeder(FetchRssService fetchRssService, ArticleDetailsService articleDetailsService, ArticleDetailMapper articleDetailMapper) {
        this.rssService = fetchRssService;
        this.articleDetailsService = articleDetailsService;
        this.articleDetailMapper = articleDetailMapper;
    }


    void seedDataRss() throws IOException {
        Instant start = Instant.now();
        log.info("[START FETCHING NEWS ARTICLES][SOURCE {}]", rssUrl);
        List<ArticleDetails> articleDetails = rssService.fetchArticleDetailsFromRss(rssUrl);
        batchInsert(articleDetails);
        Instant end = Instant.now();
        log.info("[END FETCHING AND SEEDING][DURATION: {} s]", Duration.between(start, end).toSeconds());
    }


    void seedDataIndex() {
        CompletableFuture.runAsync(() -> {
                    try {
                        batchInsert(articleDetailsService.fetchFromIndex("https://www.bisnis.com/index/page/?c=0&d=%s&per_page=%d"));
                    } catch (IOException e) {
                        log.error("{}", e.getMessage(), e);
                    }
                })
                .thenRun(() -> log.info("[SEED DATA FROM INDEX DONE]"));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @PostConstruct
    void seedData() throws IOException {
        if (enableRss) {
            seedDataRss();
        }

        if (enableIndex) {
            seedDataIndex();
        }
    }


    void batchInsert(List<ArticleDetails> articleDetails) {
        for (ArticleDetails x : articleDetails) {
            int count = articleDetailMapper.countByUrl(x.getUrl());

            if (count > 0) {
                articleDetailMapper.updateByUrl(x);
            } else if (count == 0) {
                articleDetailMapper.insertDetailArticle(x);
            }
        }
    }
}
