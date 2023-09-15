package com.ssafy.coffeeing.modules.product.service;

import com.ssafy.coffeeing.dummy.CapsuleTestDummy;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.ProductErrorInfo;
import com.ssafy.coffeeing.modules.global.security.util.SecurityContextUtils;
import com.ssafy.coffeeing.modules.product.domain.Capsule;
import com.ssafy.coffeeing.modules.product.domain.CapsuleBookmark;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.mapper.ProductMapper;
import com.ssafy.coffeeing.modules.product.repository.CapsuleBookmarkRepository;
import com.ssafy.coffeeing.modules.product.repository.CapsuleRepository;
import com.ssafy.coffeeing.modules.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

class CapsuleServiceTest extends ServiceTest {

    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Autowired
    private CapsuleBookmarkRepository capsuleBookmarkRepository;

    @MockBean
    private SecurityContextUtils securityContextUtils;

    private Capsule capsule;

    @BeforeEach
    void setUpCapsule(){
        capsule = capsuleRepository.save(CapsuleTestDummy.createMockCapsuleRoma());
    }

    @Test
    @DisplayName("캡슐 아이디를 통해 캡슐 상세 정보를 조회한다.")
    void Given_ValidCapsuleId_When_GetDetails_Then_Success() {

        // given
        CapsuleBookmark bookmark = CapsuleBookmark.builder()
                .capsule(capsule)
                .member(generalMember)
                .build();
        capsuleBookmarkRepository.save(bookmark);

        given(securityContextUtils.getMemberIdByTokenOptionalRequest()).willReturn(generalMember);
        CapsuleResponse expected = ProductMapper.supplyCapsuleResponseOf(capsule, true, null);

        // when
        CapsuleResponse actual = capsuleService.getDetail(capsule.getId());

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("존재하지 않는 캡슐 아이디를 통해 조회할 시 예외를 던진다.")
    void Given_NotFoundCapsuleId_When_GetDetails_Then_ThrowException() {

        // given
        Long invalidId = capsule.getId();
        capsuleRepository.delete(capsule);

        // when, then
        assertEquals(ProductErrorInfo.NOT_FOUND_PRODUCT,
                assertThrows(BusinessException.class, () -> capsuleService.getDetail(invalidId)).getInfo());
    }

    @Test
    @DisplayName("캡슐 아이디를 통해 캡슐을 찜한다.")
    void Given_CapsuleId_When_BookmarkingByToggleBookmark_Then_Success() {

        // given
        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        ToggleResponse actual = capsuleService.toggleBookmark(capsule.getId());

        // then
        assertTrue(actual.result());
        assertNotNull(capsuleBookmarkRepository.findByCapsuleAndMember(capsule,generalMember));
    }

    @Test
    @DisplayName("캡슐 아이디를 통해 캡슐을 찜 해제한다.")
    void Given_CapsuleId_When_CancellingBookmarkByToggleBookmark_Then_Success() {

        // given
        CapsuleBookmark bookmark = CapsuleBookmark.builder()
                .capsule(capsule)
                .member(generalMember)
                .build();
        capsuleBookmarkRepository.save(bookmark);

        given(securityContextUtils.getCurrnetAuthenticatedMember()).willReturn(generalMember);

        // when
        ToggleResponse actual = capsuleService.toggleBookmark(capsule.getId());

        // then
        assertFalse(actual.result());
        assertNull(capsuleBookmarkRepository.findByCapsuleAndMember(capsule,generalMember));
    }
}