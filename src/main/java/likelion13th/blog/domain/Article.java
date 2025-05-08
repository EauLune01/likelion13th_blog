package likelion13th.blog.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class Article {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String password;
    private LocalDateTime createdAt;

    public Article(Long id, String title, String content, String author, String password) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
