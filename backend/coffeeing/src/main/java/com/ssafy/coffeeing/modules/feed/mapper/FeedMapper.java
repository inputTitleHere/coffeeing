package com.ssafy.coffeeing.modules.feed.mapper;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.dto.FeedDetailResponse;
import com.ssafy.coffeeing.modules.feed.dto.FeedElement;
import com.ssafy.coffeeing.modules.feed.dto.FeedPageElement;
import com.ssafy.coffeeing.modules.feed.dto.FeedPageResponse;
import com.ssafy.coffeeing.modules.feed.dto.ImageElement;
import com.ssafy.coffeeing.modules.feed.dto.ProfileFeedsResponse;
import com.ssafy.coffeeing.modules.feed.dto.UploadFeedResponse;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.search.domain.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedMapper {

    public static UploadFeedResponse supplyFeedResponseBy(Feed feed) {
        return new UploadFeedResponse(feed.getId());
    }

    public static Feed supplyFeedEntityOf(Member member, String content, String imageUrl) {
        return Feed.builder()
                .member(member)
                .content(content)
                .imageUrl(imageUrl)
                .likeCount(0)
                .build();
    }

    public static Feed supplyFeedEntityOf(Member member, String content, String imageUrl, Tag tag) {
        return Feed.builder()
                .member(member)
                .content(content)
                .imageUrl(imageUrl)
                .likeCount(0)
                .tagId(tag.tagId())
                .productType(tag.category())
                .tagName(tag.name())
                .build();
    }

    public static ProfileFeedsResponse supplyFeedEntityOf(List<FeedElement> feeds, Boolean hasNext, Long nextCursor) {
        return new ProfileFeedsResponse(feeds, hasNext, nextCursor);
    }

    public static FeedDetailResponse supplyFeedDetailEntityOf(
            Feed feed,
            Tag tag,
            List<ImageElement> images,
            int likeCount,
            Boolean isLike,
            Boolean isMine) {
        return new FeedDetailResponse(
                feed.getId(),
                images,
                feed.getContent() == null ? "" : feed.getContent(),
                tag,
                feed.getMember().getId(),
                likeCount,
                feed.getMember().getNickname(),
                feed.getMember().getProfileImage(),
                isLike,
                isMine);
    }

    public static FeedPageResponse supplyFeedPageEntityOf(List<FeedPageElement> images, Boolean hasNext, Long nextCursor) {
        return new FeedPageResponse(images, hasNext, nextCursor);
    }
}
