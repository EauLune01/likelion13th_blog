package likelion13th.likelionblog.dto.response;


import likelion13th.likelionblog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ArticleDetailResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;
    private final int commentCount;
    //댓글목록 추가
    private final List<CommentResponse> comments;

    public static ArticleDetailResponse of(Article article, List<CommentResponse> comments){
        return ArticleDetailResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .createdAt(article.getCreatedAt())
                .commentCount(article.getCommentCount())
                .comments(comments)
                .build();
    }
}