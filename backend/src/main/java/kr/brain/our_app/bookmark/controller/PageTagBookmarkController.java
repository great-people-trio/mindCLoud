package kr.brain.our_app.bookmark.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.bookmark.dto.TagBookmark;
import kr.brain.our_app.bookmark.service.BookmarkService; // Bookmark를 찾기 위한 서비스
import kr.brain.our_app.tag.dto.Tag;
import kr.brain.our_app.tag.service.TagService; // Tag를 찾기 위한 서비스
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Controller
public class PageTagBookmarkController {
    private final RestTemplate restTemplate;
    private final BookmarkService bookmarkService;
    private final TagService tagService;

    @Autowired
    public PageTagBookmarkController(BookmarkService bookmarkService, TagService tagService) {
        this.restTemplate = new RestTemplate();
        this.bookmarkService = bookmarkService;
        this.tagService = tagService;
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }

    @GetMapping("/tagbookmark")
    public String bookmarkPage() {
        return "tagbookmark";
    }

//    @PostMapping("/tagbookmark")
//    public String handleTagBookmarkCreation(
//            @RequestParam Long tagId,
//            @RequestParam Long bookmarkId,
//            Model model,
//            HttpServletRequest request) {
//
//        // API 요청을 위한 URL 생성
//            String baseUrl = getBaseUrl(request);
//            String apiUrl = baseUrl + "/api/tagbookmark";
//
//            //url 확인용 임시 출력
//            System.out.println("API URL: " + apiUrl);
//
//            TagBookmark[] tagbookmarks = restTemplate.getForObject(apiUrl, TagBookmark[].class);
//            List<TagBookmark> tagbookmarkList = Arrays.asList(tagbookmarks);
//
//            model.addAttribute("tagbookmarkList", tagbookmarkList);
//            model.addAttribute("tagbookmark", new TagBookmark());
//
//        // 입력받은 tagId와 bookmarkId로 Tag와 Bookmark 찾기
//        Tag tag = tagService.findTagById(tagId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
//        Bookmark bookmark = bookmarkService.findBookmarkById(bookmarkId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 북마크를 찾을 수 없습니다."));
//
//        // RestTemplate을 사용해 Tag와 Bookmark를 결합하는 요청 전송
//        restTemplate.postForObject(apiUrl + "?tagId=" + tagId, bookmark, TagBookmark.class);
//
//        // 태그와 북마크 페이지로 리디렉션
//        return "redirect:/tagbookmark";
//    }

    @PostMapping("/tagbookmark")
    public String handleTagBookmarkCreation(
            @RequestParam Long tagId,
            @RequestParam Long bookmarkId,
            Model model,
            HttpServletRequest request) {

        // 입력받은 tagId와 bookmarkId로 Tag와 Bookmark 찾기
        Tag tag = tagService.findTagById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 태그를 찾을 수 없습니다."));
        Bookmark bookmark = bookmarkService.findBookmarkById(bookmarkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 북마크를 찾을 수 없습니다."));

        // API 요청을 위한 URL 생성 (기존 코드와 동일)
        String baseUrl = getBaseUrl(request);
        String apiUrl = baseUrl + "/api/tagbookmark";

        // RestTemplate을 사용해 Tag와 Bookmark를 결합하는 요청 전송
        restTemplate.postForObject(apiUrl + "?tagId=" + tagId, bookmark, TagBookmark.class);

        // 태그와 북마크 페이지로 리디렉션 (기존 코드와 동일)
        return "redirect:/tagbookmark";
    }
}
