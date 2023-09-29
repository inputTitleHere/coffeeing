import React, { useEffect, useState } from 'react';
import noImage from '../../assets/no_image.png';
import { FeedDetail } from 'service/feed/types';
import { privateRequest, publicRequest } from 'util/axios';
import { API_URL } from 'util/constants';
import { useOutletContext } from 'react-router-dom';
import { MemberId } from 'pages/MemberPage';
import { useSelector } from 'react-redux';
import memberSlice from 'store/memberSlice';
import { RootState } from 'store/store';

type FeedItemProps = {
  feedId: number;
  imageUrl: string;
  setters: FeedItemSetters;
};

type FeedItemSetters = {
  setIsModalOpen: (open: boolean) => void;
  setFeedDetail: (feedDetail: FeedDetail | null) => void;
};

export const FeedComponent = ({
  feedId,
  imageUrl,
  setters: { setIsModalOpen, setFeedDetail },
}: FeedItemProps) => {
  const {memberId} = useSelector((state:RootState) => state.member);
  const [isImageUsable, setIsImageUsable] = useState<boolean>(false);
  const checkImageAvailable = async (url: string) => {
    const result = await fetch(url);
    const buff = await result.blob();
    if (buff.type.startsWith('image/')) {
      setIsImageUsable(true);
    }
  };

  useEffect(() => {
    checkImageAvailable(imageUrl);
  }, []);

  const handleFeedClick = async () => {
    const data = await publicRequest(`${API_URL}/feeds/${feedId}`);
    const feedDetail: FeedDetail = data.data.data;
    console.log('regiid = ', feedDetail.registerId);
    console.log('memid = ', Number(memberId));
    // console.log(feedDetail.registerId === Number(memberId));
    if (feedDetail.registerId === Number(memberId)) {
      feedDetail.isMine=true;
    }
    setIsModalOpen(true);
    setFeedDetail(feedDetail);
  };

  return (
    <div
      className="image-wrapper border w-[200px] h-[200px] m-2 rounded-lg cursor-pointer flex items-center justify-center"
      onClick={handleFeedClick}
    >
      {isImageUsable ? (
        <img src={imageUrl} alt="이미지" />
      ) : (
        <img src={noImage} alt="이미지 실패" />
      )}
    </div>
  );
};
