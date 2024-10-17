package kr.brain.our_app.bookmark.repository;

import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.bookmark.dto.TagBookmark;
import kr.brain.our_app.tag.dto.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TagBookmarkRepository extends JpaRepository<TagBookmark, Long> {
    // Optional은 null이 될 가능성이 있을 때 사용, tag와 bookmark가 조합이 없을 경우가 있을수 있기에 사용
    Optional<TagBookmark> findByTagAndBookmark(final Tag tag, final Bookmark bookmark);

    // 특정 태그에 속하는 모든 태그-북마크 관계를 Set으로 반환
    Set<TagBookmark> findAllByTag(final Tag tag);

    // 특정 태그 ID에 해당하는 모든 태그-북마크 관계 삭제
    void deleteAllByTagId(final Long tagId);
}
