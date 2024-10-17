package kr.brain.our_app.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kr.brain.our_app.user.dto.User;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "Bookmark")
@Getter
@Setter

public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    //BOOKMARKNAME은 중복을 허용. ID는 중복 허용 X 따라서 리스트사용
    @Column
    private String bookmarkName;

    @URL
    @Column
    @Lob
    private String url;

    // User와 일대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "user_id") // Bookmark 테이블에 user_id FK 생성
    private User user;

    //태그는 중복을 허용하지않음. 따라서 SET을 사용
    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    //@JoinColumn(name ="tags_id")
    private Set<TagBookmark> tags = new HashSet<>();

    @Builder
    public Bookmark(final String url, final User user , final String bookmarkName){
        this.url = url;
        this.user = user;
        this.bookmarkName = bookmarkName;
    }
<<<<<<< Updated upstream
}

=======
}
>>>>>>> Stashed changes
