package kr.brain.our_app.tag.service;

import kr.brain.our_app.bookmark.dto.Bookmark;
import kr.brain.our_app.bookmark.repository.BookmarkRepository;
import kr.brain.our_app.tag.dto.Tag;
import kr.brain.our_app.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private TagRepository tagRepository;
    private BookmarkRepository bookmarkRepository;

    @Autowired
    public TagService(TagRepository tagRepository, BookmarkRepository bookmarkRepository) {
        this.tagRepository = tagRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }
    public List<Bookmark> getBookmarkByTagname(String tagname) {
        return bookmarkRepository.findByTags_Tag_Tagname(tagname);
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();

    }

    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }

//    public List<Bookmark> getBookmarkByTagname(String tagname){
//        List<Tag> tags = tagRepository.findByTagname(tagname);
//        return tags.stream()
//                .map(Tag::getBookmark)
//                .collect(Collectors.toList());
//    }

    //이 코드에 대해서 좀 더 고민해봐야 할것 같음.

}
