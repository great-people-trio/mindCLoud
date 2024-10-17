package kr.brain.our_app.bookmark.service;

import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.bookmark.dto.TagBookmark;
import kr.brain.our_app.bookmark.repository.TagBookmarkRepository;
import kr.brain.our_app.tag.dto.Tag;
import kr.brain.our_app.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagBookmarkService {
    private final TagBookmarkRepository tagBookmarkRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TagBookmarkService(TagBookmarkRepository tagBookmarkRepository, TagRepository tagRepository) {
        this.tagBookmarkRepository = tagBookmarkRepository;
        this.tagRepository =
                tagRepository;
    }

    // 1. 태그와 북마크를 결합하여 TagBookmark 생성
    @Transactional
    public TagBookmark createTagBookmark(Tag tag, Bookmark bookmark) {
        // 이미 존재하는 조합인지 확인 (optional 이용)
        Optional<TagBookmark> existingTagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark);
        if (existingTagBookmark.isEmpty()) {
            TagBookmark tagBookmark = new TagBookmark(tag, bookmark);
            return tagBookmarkRepository.save(tagBookmark);
        }
        throw new IllegalArgumentException("이미 존재하는 태그와 북마크 결합입니다.");
    }

    // 2. 태그에 속하는 모든 북마크를 반환
    @Transactional(readOnly = true)
    public Set<Bookmark> getBookmarksByTag(Tag tag) {
        return tagBookmarkRepository.findAllByTag(tag)
                .stream()
                .map(TagBookmark::getBookmark)
                .collect(Collectors.toSet());
    }

    // 3. 특정 태그에 속한 태그-북마크 관계 삭제
    @Transactional
    public void removeTagBookmark(Tag tag, Bookmark bookmark) {
        Optional<TagBookmark> tagBookmark = tagBookmarkRepository.findByTagAndBookmark(tag, bookmark);
        tagBookmark.ifPresent(tagBookmarkRepository::delete);
    }

    // 4. 특정 태그에 해당하는 모든 태그-북마크 결합 삭제
    @Transactional
    public void deleteAllByTagId(Long tagId) {
        tagBookmarkRepository.deleteAllByTagId(tagId);
    }
}
