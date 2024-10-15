package kr.brain.our_app.tag.controller;

import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.tag.dto.Tag;
import kr.brain.our_app.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;


    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag savedTag = tagService.createTag(tag);
        return ResponseEntity.ok(savedTag);
    }

    //태그명으로 북마크 조회
    @GetMapping("/bookmark")
    public ResponseEntity<List<Bookmark>> getBookmarkByTagname(@RequestParam String tagname) {
        List<Bookmark> bookmark = tagService.getBookmarkByTagname(tagname);
        if (bookmark.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookmark);
    }
}
