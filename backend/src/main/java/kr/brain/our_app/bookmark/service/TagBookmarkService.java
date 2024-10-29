package kr.brain.our_app.bookmark.service;

import kr.brain.our_app.bookmark.domain.Bookmark;
import kr.brain.our_app.bookmark.domain.TagBookmark;
import kr.brain.our_app.bookmark.dto.BookmarkDto;
import kr.brain.our_app.bookmark.dto.TagBookmarkDto;
import kr.brain.our_app.bookmark.repository.BookmarkRepository;
import kr.brain.our_app.bookmark.repository.TagBookmarkRepository;
import kr.brain.our_app.tag.domain.Tag;
import kr.brain.our_app.tag.dto.TagDto;
import kr.brain.our_app.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagBookmarkService {

    private final TagBookmarkRepository tagBookmarkRepository;
    private final TagRepository tagRepository;
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public TagBookmarkService(TagBookmarkRepository tagBookmarkRepository,
                              TagRepository tagRepository,
                              BookmarkRepository bookmarkRepository) {
        this.tagBookmarkRepository = tagBookmarkRepository;
        this.tagRepository = tagRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    public TagBookmarkDto createTagBookmark(TagBookmarkDto tagBookmarkDto){
        Tag tag = tagRepository.findByTagName(tagBookmarkDto.getTagName())
                .orElseThrow(IllegalArgumentException::new);
        Bookmark bookmark = bookmarkRepository.findByBookmarkName(tagBookmarkDto.getBookmarkName())
                .orElseThrow(IllegalAccessError::new);


        TagBookmark tagBookmark = new TagBookmark();
        tagBookmark.setTag(tag);
        tagBookmark.setBookmark(bookmark);

        TagBookmark savedTagBookmark = tagBookmarkRepository.save(tagBookmark);

        return TagBookmarkDto.builder()
                .tagName(savedTagBookmark.getTag().getTagName())
                .bookmarkName(savedTagBookmark.getBookmark().getBookmarkName())
                .build();
    }

    public List<TagBookmarkDto> findAllByBookmark(BookmarkDto bookmarkDto) {
        // BookmarkDto의 정보를 바탕으로 Bookmark 엔티티 조회
        Bookmark bookmark = bookmarkRepository.findByBookmarkName(bookmarkDto.getBookmarkName())
                .orElseThrow(() -> new IllegalArgumentException("Bookmark not found"));

        return tagBookmarkRepository.findAllByBookmark(bookmark)
                .stream()
                .map(tagBookmark -> TagBookmarkDto.builder()
                        .tagName(tagBookmark.getTag().getTagName())
                        .bookmarkName(tagBookmark.getBookmark().getBookmarkName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<TagBookmarkDto> findAllByTag(TagDto tagDto) {
        // TagDto의 정보를 바탕으로 Tag 엔티티 조회
        Tag tag = tagRepository.findByTagName(tagDto.getTagName())
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        return tagBookmarkRepository.findAllByTag(tag)
                .stream()
                .map(tagBookmark -> TagBookmarkDto.builder()
                        .tagName(tagBookmark.getTag().getTagName())
                        .bookmarkName(tagBookmark.getBookmark().getBookmarkName())
                        .build())
                .collect(Collectors.toList());
    }


//
//    // 1. Tag/Bookmark name으로  TagBookmark 생성 또는 기존 객체 반환
//    public TagBookmark createTagBookmark(String tagName, String bookmarkName) {
//        //TagName을 가진 Tag객체가 있는지 조회
//        Tag tag = tagRepository.findByTagName(tagName)
//                .orElseThrow(()-> new IllegalArgumentException("해당 tagname을 가진 tag가 없습니다"));
//
//        Bookmark bookmark = bookmarkRepository.findByBookmarkName(bookmarkName)
//                .orElseThrow(()-> new IllegalArgumentException("해당 bookmarkname을 가진 bookmark가 없습니다"));
//        return new TagBookmark(tag, bookmark);
//    }

//    // 2. TagId로 연결된 모든 Bookmark 조회
//    public List<Bookmark> getBookmarksByTagId(Long tagId) {
//        Tag tag = tagRepository.findById(tagId)
//                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
//
//        List<TagBookmark> tagBookmarks = tagBookmarkRepository.findByTag(tag);
//
//        // 각 TagBookmark에서 Bookmark만 추출하여 반환
//        return tagBookmarks.stream()
//                .map(TagBookmark::getBookmark)
//                .collect(java.util.stream.Collectors.toList());
//    }
//
//    // 3. TagName으로 연결된 모든 Bookmark 조회
//    public List<Bookmark> getBookmarksByTagName(String tagName) {
//        Optional<Tag> tag = tagRepository.findByTagName(tagName); // 단일 Tag 반환
//
//        if (tag == null) {
//            throw new IllegalArgumentException("해당 TagName을 가진 Tag 객체가 존재하지 않음");
//        }
//
//    }

//    // 4. BookmarkId로 연결된 모든 Tag 조회
//    public List<Tag> getTagsByBookmarkId(Long bookmarkId) {
//        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
//                .orElseThrow(() -> new IllegalArgumentException("Bookmark not found"));
//
//        List<TagBookmark> tagBookmarks = tagBookmarkRepository.findByBookmark(bookmark);
//
//        // 각 TagBookmark에서 Tag만 추출하여 반환
//        return tagBookmarks.stream()
//                .map(TagBookmark::getTag)
//                .collect(java.util.stream.Collectors.toList());
//    }
//
//    // 5. BookmarkName으로 연결된 모든 Tag 조회
//    public List<Tag> getTagsByBookmarkName(String bookmarkName) {
//        // BookmarkRepository에서 List<Bookmark>가 반환된다고 가정
//        List<Bookmark> bookmarks = bookmarkRepository.findByBookmarkName(bookmarkName);
//
//        // 빈 리스트일 경우 예외 처리
//        if (bookmarks.isEmpty()) {
//            throw new IllegalArgumentException("Bookmark not found");
//        }
//
//        // 첫 번째 북마크의 ID를 사용해 태그를 조회
//        Bookmark bookmark = bookmarks.get(0); // 만약 여러 북마크가 있으면 첫 번째만 사용 (필요시 수정)
//        return getTagsByBookmark(bookmark.getId());
//    }


//    // 태그 이름으로 북마크 가져오기
//    public List<Bookmark> getBookmarksByTagName(String tagName) {
//        // 태그명을 통해 태그를 찾음
//        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(()
//                -> new IllegalArgumentException("Tag not found with name: " + tagName));
//
//        // 해당 태그로 연결된 TagBookmark에서 북마크를 가져옴
//        List<TagBookmark> tagBookmarks = tagBookmarkRepository.findByTag(tag);
//
//        // TagBookmark에서 Bookmark 객체들을 추출하여 반환
//        return tagBookmarks.stream()
//                .map(TagBookmark::getBookmark)
//                .collect(Collectors.toList());
//    }
}
