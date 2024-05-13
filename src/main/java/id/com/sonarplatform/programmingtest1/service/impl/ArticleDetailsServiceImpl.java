package id.com.sonarplatform.programmingtest1.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 9:56 PM
@Last Modified 5/13/2024 9:56 PM
Version 1.0
*/

import id.com.sonarplatform.programmingtest1.model.ArticleDetails;
import id.com.sonarplatform.programmingtest1.repository.ArticleDetailMapper;
import id.com.sonarplatform.programmingtest1.service.ArticleDetailsService;
import id.com.sonarplatform.programmingtest1.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticleDetailsServiceImpl implements ArticleDetailsService {

    private final ArticleDetailMapper articleDetailMapper;

    @Autowired
    public ArticleDetailsServiceImpl(ArticleDetailMapper articleDetailMapper) {
        this.articleDetailMapper = articleDetailMapper;
    }

    @Override
    public List<ArticleDetails> getAll(int page, int size) {
        return articleDetailMapper.getAll(page, size);
    }

    @Override
    public ArticleDetails getByUrl(String url) {
        return articleDetailMapper.getByUrl(url);
    }

    @Override
    public ArticleDetails getById(int id) {
        return articleDetailMapper.getById(id);
    }

    @Override
    public List<ArticleDetails> fetchFromIndex(String url) throws IOException {
        int currentPage = 0;
        String currentDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_DATE);

        List<ArticleDetails> data = new ArrayList<>();
        while (true) {
            Document news = Jsoup.connect(String.format(url, currentDate, currentPage)).get();
            Element ul = news.selectFirst(".list-news");
            Elements lis = Optional.ofNullable(ul).map(el -> el.select("li")).orElse(null);

            if (lis == null || lis.get(0).text().contains("Tidak ada berita")) {
                break;
            }

            for (Element item : lis) {
                data.add(mapIndexToArticleDetails(item));
            }
            currentPage++;
        }
        return data;
    }

    private ArticleDetails mapIndexToArticleDetails(Element item) {
        ArticleDetails articleDetails = new ArticleDetails();
        Element a = item.selectFirst("a");

        articleDetails.setUrl(Optional.ofNullable(a).map(tag -> tag.attr("href")).orElse(null));
        articleDetails.setTitle(Optional.ofNullable(a).map(tag -> tag.attr("title")).orElse(null));

        try {
            // Set publish time
            Element pubDate = item.selectFirst("div.date");
            articleDetails.setPubDate(Optional.ofNullable(pubDate).map(pd -> DateUtils.getDateFromString(pd.text())).orElse(null));
            articleDetails.setPublicationTime(Optional.ofNullable(pubDate).map(pd -> DateUtils.getDateFromString(pd.text())).map(Date::getTime).orElse(0L));

            // Get Content
            articleDetails.setContent(getContentFromWeb(articleDetails.getUrl()));
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        return articleDetails;
    }

    private String getContentFromWeb(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element content = doc.selectFirst(".detailsContent");

        if (content == null) {
            content = doc.selectFirst(".wrapper-desc");
        }
        return Optional.ofNullable(content).map(Element::text).orElse(null);
    }
}