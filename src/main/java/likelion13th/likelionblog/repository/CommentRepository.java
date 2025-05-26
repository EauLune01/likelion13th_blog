package likelion13th.likelionblog.repository;

import likelion13th.likelionblog.domain.Article;
import likelion13th.likelionblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
    // JpaRepository 클래스 상속받음
    // 엔티티 Comment와 기본키 타입을 인수로
    List<Comment> findByArticle(Article article);
}
