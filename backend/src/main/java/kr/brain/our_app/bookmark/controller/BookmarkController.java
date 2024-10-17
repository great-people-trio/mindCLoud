package kr.brain.our_app.bookmark.controller;
import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.bookmark.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService){
        this.bookmarkService = bookmarkService;
    }

    //북마크 생성
    @PostMapping
    public Bookmark createBookmark(@RequestBody Bookmark bookmark){
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        return bookmarkService.createBookmark(bookmark);
    }
    //모든 북마크 조회
    @GetMapping
    public List<Bookmark> getAllBookmark(){
<<<<<<< Updated upstream
        return bookmarkService.getAllBookmark();
    }
    //이름으로 북마크 조회
   @GetMapping("/search")
    public List<Bookmark> getBookmarkByName(@RequestParam String bookmarkName){
        return bookmarkService.getBookmarkByName(bookmarkName);
   }
   //태그로 북마크 조회
=======

        return bookmarkService.getAllBookmark();
    }
    //이름으로 북마크 조회
    @GetMapping("/search")
    public List<Bookmark> getBookmarkByName(@RequestParam String bookmarkName){
        return bookmarkService.getBookmarkByName(bookmarkName);
    }
    //태그로 북마크 조회
>>>>>>> Stashed changes
    @GetMapping("/tag")
    public List<Bookmark> getBookmarkByTag(@RequestParam String tag){
        return bookmarkService.getBookmarkByTagName(tag);
    }
    //북마크 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id){
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.noContent().build();
    }

<<<<<<< Updated upstream
}
=======

}
>>>>>>> Stashed changes
