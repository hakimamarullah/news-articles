package id.com.sonarplatform.programmingtest1.repository;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 7:50 PM
@Last Modified 5/13/2024 7:50 PM
Version 1.0
*/

import id.com.sonarplatform.programmingtest1.model.ArticleDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDetailMapper {

    int insertDetailArticle(ArticleDetails articleDetails);

    List<ArticleDetails> getAll(@Param("offset") int page, @Param("limit") int size);

    ArticleDetails getByUrl(String url);
    ArticleDetails getById(long id);

    int updateByUrl(ArticleDetails articleDetails);

    int countByUrl(String url);

    int getTotalRecords();


}
