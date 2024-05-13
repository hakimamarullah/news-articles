package id.com.sonarplatform.programmingtest1.model.response;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/13/2024 9:59 PM
@Last Modified 5/13/2024 9:59 PM
Version 1.0
*/

import lombok.Data;

@Data
public class SimpleResponse<T> {
    private int page;
    private int size;
    private int totalPage;
    private T data;
}
