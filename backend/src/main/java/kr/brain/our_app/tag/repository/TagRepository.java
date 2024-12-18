package kr.brain.our_app.tag.repository;

import kr.brain.our_app.tag.domain.Tag;
import kr.brain.our_app.tag.dto.TagDto;
import kr.brain.our_app.user.domain.User;
import kr.brain.our_app.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {

    Optional<Tag> findByTagName(String tagName);
    List<Tag> findAllByUser_Id(String userId);
    Optional<Tag> findByTagNameAndUser_Id(String tagName, String userId);
    boolean existsByTagName(String tagName);
    boolean existsByTagNameAndUser_Id(String tagName, String userId);

}
