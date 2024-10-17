package kr.brain.our_app.tag.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Entity
@Table(name ="Tag")
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="tag_id")
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = 25, unique = true)
    private String tagname;

    public Tag() {
        // 기본 생성자는 아무 동작을 하지 않거나 초기값을 설정 할 수 있음
    }

    @Builder
    public Tag( final String tagname) {
        this.tagname = tagname;
    }

}
