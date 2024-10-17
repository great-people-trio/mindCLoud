//package kr.brain.our_app.bookmark.controller;
//
//import kr.brain.our_app.bookmark.dto.Bookmark;
//import kr.brain.our_app.bookmark.dto.TagBookmark;
//import kr.brain.our_app.bookmark.service.TagBookmarkService;
//import kr.brain.our_app.tag.dto.Tag;
//import kr.brain.our_app.tag.repository.TagRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.hamcrest.Matchers.containsString;
//
//@WebMvcTest(TagBookmarkController.class)
//public class TagBookmarkControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TagBookmarkService tagBookmarkService;
//
//    @MockBean
//    private TagRepository tagRepository;
//
//    private Tag tag;
//    private Bookmark bookmark;
//    private TagBookmark tagBookmark;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//
//        tag = new Tag();
//        tag.setId(1L);
//        tag.setTagname("Technology");
//
//
//        bookmark = new Bookmark();
//        bookmark.setId(1L);
//        bookmark.setUrl("http://example.com");
//
//        tagBookmark = new TagBookmark(tag, bookmark);
//    }
//
//    @Test
//    public void testCreateTagBookmark() throws Exception {
//        when(tagRepository.findById(any(Long.class))).thenReturn(Optional.of(tag));
//        when(tagBookmarkService.createTagBookmark(any(Tag.class), any(Bookmark.class))).thenReturn(tagBookmark);
//
//        mockMvc.perform(post("/api/bookmarks/tags")
//                        .param("tagId", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"url\": \"http://example.com\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("http://example.com")));
//    }
//
//    @Test
//    public void testGetBookmarksByTag() throws Exception {
//        Pageable pageable = PageRequest.of(0, 10);
//        when(tagRepository.findById(eq(1L))).thenReturn(Optional.of(tag));
//        when(tagBookmarkService.getBookmarksByTag(any(Tag.class), any(Pageable.class)))
//                .thenReturn(new PageImpl<>(Collections.singletonList(tagBookmark), pageable, 1));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookmarks/tags/1/bookmarks")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testRemoveTagBookmark() throws Exception {
//        when(tagRepository.findById(any(Long.class))).thenReturn(Optional.of(tag));
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookmarks/tags")
//                        .param("tagId", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"url\": \"http://example.com\"}"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    public void testDeleteAllTagBookmarks() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookmarks/tags/1/all-bookmarks"))
//                .andExpect(status().isNoContent());
//    }
//}