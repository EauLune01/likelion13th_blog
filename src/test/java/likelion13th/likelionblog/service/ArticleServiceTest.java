package likelion13th.likelionblog.service;

import likelion13th.likelionblog.domain.Article;
import likelion13th.likelionblog.dto.request.AddArticleRequest;
import likelion13th.likelionblog.dto.request.DeleteRequest;
import likelion13th.likelionblog.dto.response.ArticleResponse;
import likelion13th.likelionblog.exception.ArticleNotFoundException;
import likelion13th.likelionblog.repository.ArticleRepository;
import likelion13th.likelionblog.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentRepository commentRepository;

    @DisplayName("addArticle() : 게시글 생성 성공")
    @Test
    void addArticle() {
        // given
        AddArticleRequest addArticleRequest = new AddArticleRequest(
                "제목입니다",
                "내용입니다",
                "글쓴이입니다",
                "비번입니다1234"
        );

        // 테스트용 Article 객체 생성 (id 수동 세팅 필요)
        Article article = addArticleRequest.toEntity();

        // id는 JPA save 후에 붙는 거니까 수동 세팅
        ReflectionTestUtils.setField(article, "id", 1L);

        // save() 호출 시 article 그대로 반환되도록 설정
        given(articleRepository.save(any(Article.class))).willReturn(article);

        // when
        ArticleResponse response = articleService.addArticle(addArticleRequest);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("제목입니다");
        assertThat(response.getContent()).isEqualTo("내용입니다");
    }
    /*@DisplayName("addArticle() : 게시글 생성 성공")
    @Test
    void addArticle() {
        //given
        AddArticleRequest addArticleRequest = new AddArticleRequest(
                "제목입니다",
                "내용입니다",
                "글쓴이입니다",
                "비번입니다1234"
        );

        Article article = Article.builder()
                .id(1L)
                .title("제목입니다")
                .content("내용입니다")
                .author("글쓴이입니다")
                .password("비번입니다1234")
                .createdAt(LocalDateTime.now())
                .commentCount(0)
                .build();

        given(articleRepository.save(any(Article.class))).willReturn(article);

        //when
        ArticleResponse response=articleService.addArticle(addArticleRequest);

        //then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("제목입니다");
        assertThat(response.getContent()).isEqualTo("내용내용");


    }*/

    @DisplayName("deleteArticle() 테스트 : 게시글 삭제 실패")
    @Test
    void deleteArticle_notFound(){
        //given
        DeleteRequest deleteRequest=new DeleteRequest("비번입니다");
        Long id=999L;
        given(articleRepository.findById(id)).willReturn(Optional.empty());

        //when-then
        assertThatThrownBy(()->articleService.deleteArticle(id,deleteRequest))
                .isInstanceOf(ArticleNotFoundException.class);

        verify(articleRepository, never()).deleteById(anyLong());  //deleteById 메서드가 한번도 실행되지 않았는지 확인
    }
}
