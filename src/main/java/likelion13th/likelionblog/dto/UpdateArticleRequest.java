package likelion13th.likelionblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public class UpdateArticleRequest {
    private String title;
    private String content;
    private String password;
}
