package kr.brain.our_app.bookmark.dto;

//TAGGLE에서는 implements Serializable(직렬화)로 객체 저장/전송
//네트워크 전송/파일전달, 캐싱, 세션관리에서 이득이 있음

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kr.brain.our_app.tag.dto.Tag;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "Tag_Bookmark")
@Getter
@Setter
public class TagBookmark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_bookmark_id")
    private long id;

    //여기서 다대일 관계가 좀 헷갈릴 수 있는데, 여러개의 TB가 한개의 T에 연결
    //즉 tag 하나 조회하면 여러개의 TB가 나오기에 TB가 T에 의존적이다.

    //(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "tag_id")
    //TB 테이블에 tag_id라는 이름의 컬럼을 사용해서 Tag 엔티티를 참조한다.
    // 이 컬럼이 외래키 역할을 하고, Tag의 기본키(id)와 연결된다.
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    public TagBookmark() {
        //기본생성자
    }

    public TagBookmark(final Tag tag, final Bookmark bookmark) {
        this.tag = tag;
        this.bookmark = bookmark;
    }
}
