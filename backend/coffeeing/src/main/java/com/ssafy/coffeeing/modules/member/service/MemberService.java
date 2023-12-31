package com.ssafy.coffeeing.modules.member.service;

import com.ssafy.coffeeing.modules.event.eventer.ExperienceEvent;
import com.ssafy.coffeeing.modules.global.embedded.CoffeeCriteria;
import com.ssafy.coffeeing.modules.member.dto.*;
import com.ssafy.coffeeing.modules.member.mapper.MemberMapper;
import com.ssafy.coffeeing.modules.member.util.MemberUtil;
import com.ssafy.coffeeing.modules.survey.domain.Preference;
import com.ssafy.coffeeing.modules.survey.dto.CoffeeCriteriaResponse;
import com.ssafy.coffeeing.modules.survey.repository.PreferenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.MemberErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.member.domain.Member;
import com.ssafy.coffeeing.modules.member.domain.MemberState;
import com.ssafy.coffeeing.modules.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;
    private final SecurityContextUtils securityContextUtils;
    private final MemberUtil memberUtil;

    private static final int MAX_LEVEL = 3;

    @Transactional(readOnly = true)
    public ExistNickNameResponse checkDuplicateNickname(final String nickname) {
        return new ExistNickNameResponse(memberRepository.existsByNickname(nickname));
    }

    @Transactional
    public OnboardResponse insertAdditionalMemberInfo(final OnboardRequest onboardRequest) {
        if (checkDuplicateNickname(onboardRequest.nickname()).exist()) {
            throw new BusinessException(MemberErrorInfo.PRE_EXIST_NICKNAME);
        }

        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        if (!member.getState().equals(MemberState.BEFORE_ADDITIONAL_DATA)) {
            throw new BusinessException(MemberErrorInfo.NOT_VALID_STATE);
        }

        member.updateMemberState(MemberState.NORMAL);
        member.updateByOnboardResult(onboardRequest.nickname(), onboardRequest.ageIdx(), onboardRequest.genderIdx());
        return new OnboardResponse(member.getId(), member.getNickname());
    }

    public void addExperience(final ExperienceEvent eventRecord) {
        Member member = memberRepository.findById(eventRecord.memberId()).orElseThrow(() -> new BusinessException(MemberErrorInfo.NOT_FOUND));
        if (member.getMemberLevel() < MAX_LEVEL) {
            member.addExperience(eventRecord.experience());
        }
        while (isLevelUp(member.getMemberLevel(), member.getExperience()) && member.getMemberLevel() < MAX_LEVEL) {
            member.subtractExperience(memberUtil.calculateLevelUpExperience(member.getMemberLevel()));
            member.levelUp();
        }
        if (member.getMemberLevel() == MAX_LEVEL) {
            member.setExperience(memberUtil.calculateLevelUpExperience(MAX_LEVEL));
        }
        memberRepository.save(member);
    }


    private boolean isLevelUp(int level, int experience) {
        return experience >= memberUtil.calculateLevelUpExperience(level);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorInfo.NOT_FOUND));
        Preference preference = preferenceRepository.findByMemberId(memberId);
        CoffeeCriteriaResponse coffeeCriteriaResponse = MemberMapper.supplyCoffeeCriteriaResponseFrom(preference);
        return MemberMapper.supplyBaseInfoResponseOf(member, coffeeCriteriaResponse);
    }

    @Transactional(readOnly = true)
    public ExperienceInfoResponse getMemberExperience(Long memberId) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorInfo.NOT_FOUND));
        return MemberMapper.supplyExperienceInfoResponseOf(
                member,
                memberUtil.calculateLevelUpExperience(member.getMemberLevel())
        );
    }

    @Transactional
    public void updateMemberProfileImage(ProfileImageChangeRequest profileImageChangeRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        member.updateProfileImage(profileImageChangeRequest.profileImageUrl());
    }

    @Transactional
    public void updateMemberNickname(NicknameChangeRequest nicknameChangeRequest) {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        boolean nicknameExists = memberRepository.existsByNickname(nicknameChangeRequest.nickname());
        if (nicknameExists) {
            throw new BusinessException(MemberErrorInfo.PRE_EXIST_NICKNAME);
        } else {
            member.updateMemberNickname(nicknameChangeRequest.nickname());
        }
    }

    @Transactional(readOnly = true)
    public MyInfoResponse getCurrentMemberInfo() {
        Member member = securityContextUtils.getCurrnetAuthenticatedMember();
        boolean isAfterSurvey = preferenceRepository.findByMemberId(member.getId()) != null;
        return MemberMapper.supplyMyInfoResponseOf(member, isAfterSurvey);
    }
}
