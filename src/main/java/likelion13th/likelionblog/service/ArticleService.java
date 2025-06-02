package likelion13th.likelionblog.service;

import jakarta.transaction.Transactional;
import likelion13th.likelionblog.domain.Article;
import likelion13th.likelionblog.dto.request.AddArticleRequest;
import likelion13th.likelionblog.dto.request.DeleteRequest;
import likelion13th.likelionblog.dto.request.UpdateArticleRequest;
import likelion13th.likelionblog.dto.response.ArticleDetailResponse;
import likelion13th.likelionblog.dto.response.ArticleResponse;
import likelion13th.likelionblog.dto.response.CommentResponse;
import likelion13th.likelionblog.dto.response.SimpleArticleResponse;
import likelion13th.likelionblog.exception.ArticleNotFoundException;
import likelion13th.likelionblog.exception.PermissionDeniedException;
import likelion13th.likelionblog.repository.ArticleRepository;
import likelion13th.likelionblog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    //단일 글 조회
    public ArticleDetailResponse getArticle(Long id){
         /*1. JPA의 findById()를 사용하여 DB에서 id가 일치하는 게시글 찾기.
              id가 일치하는 게시글이 DB에 없으면 에러 반환*/
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다. ID: "+id));

        /*2. 해당 게시글에 달려있는 댓글들 가져오기*/
        List<CommentResponse> comments=getCommentList(article);

        //3. ArticleResponse DTO 생성하여 반환
        return ArticleDetailResponse.of(article,comments);
    }

    //특정 게시글에 달려있는 댓글목록 가져오기
    private List<CommentResponse> getCommentList(Article article){
        return commentRepository.findByArticle(article).stream()
                .map(comment->CommentResponse.of(comment))
                .toList();
    }

    //게시글 생성
    public ArticleResponse addArticle(AddArticleRequest request){

        /*Article article = request.toEntity();
        articleRepository.save(article); 이 id가 부여된 객체를 Article 타입 변수에 담아야 함
        return ArticleResponse.of(article); 기존 코드: 여기서의 article은 id 부여가 안된 객체*/

        Article article = articleRepository.save(request.toEntity());
        return ArticleResponse.of(article);
    }

    //전체 글 조회
    public List<SimpleArticleResponse> getAllArticles(){
        /*1. JPA의 findAll() 을 사용하여 DB에 저장된 전체 Article을 List 형태로 가져오기*/
        List<Article> articleList = articleRepository.findAll();

        /*2. Article -> SimpleArticleResponse : 엔티티를 DTO로 변환*/
        List<SimpleArticleResponse> articleResponseList = articleList.stream()
                .map(article -> SimpleArticleResponse.of(article))
                .toList();

        /*3. articleResponseList (DTO 리스트) 반환 */
        return articleResponseList;
    }

    //글 수정
    @Transactional
    public ArticleResponse updateArticle(Long id, UpdateArticleRequest request)  {

        /* 1. 요청이 들어온 게시글 ID로 데이터베이스에서 게시글 찾기. 해당하는 게시글이 없으면 에러*/
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        /*2. 비밀번호 일치하는지 확인 : 요청을 보낸 사람이 이 게시글의 수정 권한을 가지고 있는지
            request.getPassword() : 게시글 수정 요청을 보낸 사람이 입력한 비밀번호
            article.getPassword() : 데이터베이스에 저장된 비밀번호 (작성자가 글 쓸때 등록한)
         */
        if(!article.getPassword().equals(request.getPassword())){
            throw new PermissionDeniedException("해당 글에 대한 수정 권한이 없습니다.");
        }

        /*3. 게시글 수정 후 저장 */
        article.update(request.getTitle(),request.getContent());
        article=articleRepository.save(article);

        /* ArticleResponse로 변환해서 리턴 */
        return ArticleResponse.of(article);

    }

    //글 삭제
    @Transactional
    public void deleteArticle(Long id, DeleteRequest request){
        /* 1. 요청이 들어온 게시글 ID로 데이터베이스에서 게시글 찾기. 해당하는 게시글이 없으면 에러*/
        Article article=articleRepository.findById(id)
                .orElseThrow(()-> new ArticleNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        /*2. 비밀번호 일치하는지 확인 : 요청을 보낸 사람이 이 게시글의 삭제권한을 가지고 있는지
            request.getPassword() : 게시글 수정 요청을 보낸 사람이 입력한 비밀번호
            article.getPassword() : 데이터베이스에 저장된 비밀번호 (작성자가 글 쓸때 등록한)
         */
        if(!request.getPassword().equals(article.getPassword())){
            throw new PermissionDeniedException("해당 글에 대한 삭제 권한이 없습니다.");
        }

        /*3. 게시글 삭제 */
        articleRepository.deleteById(id);
    }

}