package id.com.sonarplatform.programmingtest1.model;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 11:26 AM
@Last Modified 5/13/2024 11:26 AM
Version 1.0
*/

import lombok.Data;

@Data
public class ArticleDetails {

    private String title;
    private String url;
    private long publicationTime;
    private String content;
}
