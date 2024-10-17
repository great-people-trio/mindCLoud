package kr.brain.our_app.bookmark.controller;

import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.bookmark.dto.TagBookmark;
import kr.brain.our_app.bookmark.service.TagBookmarkService;
import kr.brain.our_app.tag.dto.Tag;
import kr.brain.our_app.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/tagbookmark")
public class TagBookmarkController {
    private final TagBookmarkService tagBookmarkService;
    private final TagRepository tagRepository;

    @Autowired
    public TagBookmarkController(TagBookmarkService tagBookmarkService, TagRepository tagRepository) {
        this.tagBookmarkService = tagBookmarkService;
        this.tagRepository = tagRepository;
    }

    // 1. 태그와 북마크의 결합 생성
//    @PostMapping
//    public ResponseEntity<TagBookmark> createTagBookmark(@RequestParam Long tagId, @RequestBody Bookmark bookmark) {
//        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
//                new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
//        TagBookmark tagBookmark = tagBookmarkService.createTagBookmark(tag, bookmark);
//        return ResponseEntity.ok(tagBookmark);
//    }

    @PostMapping
    public ResponseEntity<TagBookmark> createTagBookmark(
            @RequestParam Long tagId,
            @RequestBody Bookmark bookmark) {

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
        TagBookmark tagBookmark = tagBookmarkService.createTagBookmark(tag, bookmark);
        return ResponseEntity.ok(tagBookmark);
    }

    // 2. 특정 태그에 속한 북마크들을 모두 조회
    @GetMapping("/{tagId}/bookmarks")
    public ResponseEntity<Set<Bookmark>> getBookmarksByTag(@PathVariable Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() ->
                new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
        Set<Bookmark> bookmarks = tagBookmarkService.getBookmarksByTag(tag);
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

<<<<<<< Updated upstream
    // 4. 특정 태그와 관련된 모든 결합 삭제
=======
    // 4. 특정 태그와 관련된 모든 결합 삭제 -> 태그 삭제 시 결합 모두 삭제, 이거 태그에서 구현 해야함
>>>>>>> Stashed changes
    @DeleteMapping("/{tagId}/all-bookmarks")
    public ResponseEntity<Void> deleteAllTagBookmarks(@PathVariable Long tagId) {
        tagBookmarkService.deleteAllByTagId(tagId);
        return ResponseEntity.noContent().build();
    }
}
