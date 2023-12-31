package com.ssafy.coffeeing.modules.feed.repository;

import com.ssafy.coffeeing.modules.feed.domain.Feed;
import com.ssafy.coffeeing.modules.feed.domain.FeedLike;
import com.ssafy.coffeeing.modules.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    Optional<FeedLike> findFeedLikeByFeedAndMember(Feed feed, Member member);

    void deleteFeedLikesByFeed(Feed feed);
}
