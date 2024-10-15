package kr.brain.our_app.bookmark.controller;

import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.bookmark.dto.TagBookmark;
import kr.brain.our_app.bookmark.service.TagBookmarkService;
import kr.brain.our_app.tag.dto.Tag;
import kr.brain.our_app.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks/tags")
public class TagBookmarkController {

    private final TagBookmarkService tagBookmarkService;
    private final TagRepository tagRepository;

    @Autowired
    public TagBookmarkController(TagBookmarkService tagBookmarkService, TagRepository tagRepository) {
        this.tagBookmarkService = tagBookmarkService;
        this.tagRepository = tagRepository;
    }

    // 1. 태그와 북마크의 결합 생성
    @PostMapping
    public ResponseEntity<TagBookmark> createTagBookmark(@RequestParam Long tagId, @RequestBody Bookmark bookmark) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
        TagBookmark tagBookmark = tagBookmarkService.createTagBookmark(tag, bookmark);
        return ResponseEntity.ok(tagBookmark);
    }

    // 2. 특정 태그에 속한 북마크들을 페이징 형식으로 조회
    @GetMapping("/{tagId}/bookmarks")
    public ResponseEntity<Page<TagBookmark>> getBookmarksByTag(@PathVariable Long tagId, Pageable pageable) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
        Page<TagBookmark> bookmarks = tagBookmarkService.getBookmarksByTag(tag, pageable);

        return ResponseEntity.ok(bookmarks);
    }

    // 3. 태그와 북마크의 결합 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeTagBookmark(@RequestParam Long tagId, @RequestBody Bookmark bookmark) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
        tagBookmarkService.removeTagBookmark(tag, bookmark);
        return ResponseEntity.noContent().build();
    }

    // 4. 특정 태그와 관련된 모든 결합 삭제
    @DeleteMapping("/{tagId}/all-bookmarks")
    public ResponseEntity<Void> deleteAllTagBookmarks(@PathVariable Long tagId) {
        tagBookmarkService.deleteAllByTagId(tagId);
        return ResponseEntity.noContent().build();
    }
}