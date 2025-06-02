package likelion13th.likelionblog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateArticleRequest {
    private String title;
    private String content;
    private String password;
}
