package likelion13th.likelionblog.dto;

import likelion13th.likelionblog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;
    private String author;
    private String password;

    public Article toEntity(){
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .password(password)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
