package likelion13th.likelionblog.repository;


import likelion13th.likelionblog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    // JpaRepository 클래스 상속받음
    // 엔티티 Article과 기본키 타입을 인수로

    // Article 제목 기반으로 객체 탐색
    Optional<Article> findByTitle(String title);
}