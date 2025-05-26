package likelion13th.likelionblog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity //해당 클래스가 JPA가 관리할 수 있는 Entity임을 의미
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id // id 필드를 기본키(Primary Key)로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(updatable = false) //수정 불가
    private Long id;

    @Column(nullable = false) //not null
    private String title;

    @Column(nullable = false) //not null
    private String content;

    @Column(nullable = false) //not null
    private String password;

    @Column(nullable = false, updatable = false) //not null, 수정 불가
    private LocalDateTime createdAt;

    @Column(nullable = false) //not null
    private String author;

    @Column(nullable = false)
    private int commentCount;

    //constructor
    public Article(String title, String content, String author,  String password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    //수정 메서드
    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }

    public void increaseCommentCount() {
        commentCount++;
    }
    public void decreaseCommentCount() {
        if(commentCount>0) commentCount--;
    }


}
