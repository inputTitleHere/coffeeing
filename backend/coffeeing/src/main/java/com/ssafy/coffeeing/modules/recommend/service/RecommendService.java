package com.ssafy.coffeeing.modules.recommend.service;

import com.ssafy.coffeeing.modules.member.domain.Age;
import com.ssafy.coffeeing.modules.member.domain.Gender;
import com.ssafy.coffeeing.modules.recommend.dto.RecommendResponse;
import com.ssafy.coffeeing.modules.survey.dto.PreferenceRequest;

public interface RecommendService {

    RecommendResponse pickByPreference(PreferenceRequest preferenceRequest);

    RecommendResponse pickBySimilarity(Boolean isCapsule, Long id);

    RecommendResponse pickByCriteria(Boolean isCapsule, String criteria, String attribute);

}