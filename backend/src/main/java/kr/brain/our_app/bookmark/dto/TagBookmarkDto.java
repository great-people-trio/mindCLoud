package kr.brain.our_app.bookmark.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagBookmarkDto {
    private String tagId;
    private String bookmarkId;
}


