//package kr.brain.our_app.bookmark.service;
//
//import kr.brain.our_app.bookmark.dto.Bookmark;
//import kr.brain.our_app.bookmark.dto.TagBookmark;
//import kr.brain.our_app.bookmark.repository.TagBookmarkRepository;
//import kr.brain.our_app.tag.dto.Tag;
//import kr.brain.our_app.tag.repository.TagRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class TagBookmarkServiceTest {
//
//    @Mock
//    private TagBookmarkRepository tagBookmarkRepository;
//
//    @Mock
//    private TagRepository tagRepository;
//
//    @InjectMocks
//    private TagBookmarkService tagBookmarkService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateTagBookmark() {
//        Tag tag = new Tag("Java");
//        Bookmark bookmark = new Bookmark();
//        bookmark.setUrl("http://example.com");
//
//        TagBookmark tagBookmark = new TagBookmark(tag, bookmark);
//
//        when(tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)).thenReturn(Optional.empty());
//        when(tagBookmarkRepository.save(any(TagBookmark.class))).thenReturn(tagBookmark);
//
//        TagBookmark result = tagBookmarkService.createTagBookmark(tag, bookmark);
//
//        assertNotNull(result);
//        assertEquals("Java", result.getTag().getTagname());
//        assertEquals("http://example.com", result.getBookmark().getUrl());
//    }
//
//    @Test
//    public void testGetBookmarksByTag() {
//        Tag tag = new Tag("Java");
//        Bookmark bookmark = new Bookmark();
//        bookmark.setUrl("http://example.com");
//
//        TagBookmark tagBookmark = new TagBookmark(tag, bookmark);
//        List<TagBookmark> tagBookmarks = List.of(tagBookmark);
//        Pageable pageable = PageRequest.of(0, 1);
//        Page<TagBookmark> page = new PageImpl<>(tagBookmarks);
//
//       // when(tagBookmarkRepository.findAllByTag(tag).thenReturn(page);
//
//      //  Page<TagBookmark> result = tagBookmarkService.getBookmarksByTag(tag, pageable);
//
//        assertNotNull(result);
//        assertEquals(1, result.getTotalElements());
//    }
//
//    @Test
//    public void testRemoveTagBookmark() {
//        Tag tag = new Tag("Java");
//        Bookmark bookmark = new Bookmark();
//
//        TagBookmark tagBookmark = new TagBookmark(tag, bookmark);
//
//        when(tagBookmarkRepository.findByTagAndBookmark(tag, bookmark)).thenReturn(Optional.of(tagBookmark));
//
//       // tagBookmarkService.removeTagBookmark(tag, bookmark);
//
//        verify(tagBookmarkRepository, times(1)).delete(tagBookmark);
//    }
//
//    @Test
//    public void testDeleteAllByTagId() {
//        Long tagId = 1L;
//
//        doNothing().when(tagBookmarkRepository).deleteAllByTagId(tagId);
//
//        //tagBookmarkService.deleteAllByTagId(tagId);
//
//        verify(tagBookmarkRepository, times(1)).deleteAllByTagId(tagId);
//    }
//}