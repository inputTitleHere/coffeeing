package com.ssafy.coffeeing.modules.product.controller;

import com.ssafy.coffeeing.modules.global.dto.CreationResponse;
import com.ssafy.coffeeing.modules.global.dto.ToggleResponse;
import com.ssafy.coffeeing.modules.product.dto.CapsuleResponse;
import com.ssafy.coffeeing.modules.product.dto.CoffeeResponse;
import com.ssafy.coffeeing.modules.product.dto.PageInfoRequest;
import com.ssafy.coffeeing.modules.product.dto.ProductReviewResponse;
import com.ssafy.coffeeing.modules.product.dto.ReviewRequest;
import com.ssafy.coffeeing.modules.product.dto.SimilarProductResponse;
import com.ssafy.coffeeing.modules.product.service.CapsuleReviewService;
import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.product.service.CoffeeReviewService;
import com.ssafy.coffeeing.modules.product.service.CoffeeService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {

    private final CapsuleService capsuleService;

    private final CoffeeService coffeeService;

    private final CapsuleReviewService capsuleReviewService;

    private final CoffeeReviewService coffeeReviewService;

    @GetMapping("/capsule/{id}")
    public BaseResponse<CapsuleResponse> getCapsuleDetail(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<CapsuleResponse>builder()
                .data(capsuleService.getDetail(id))
                .build();
    }

    @GetMapping("/coffee/{id}")
    public BaseResponse<CoffeeResponse> getCoffeeDetail(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<CoffeeResponse>builder()
                .data(coffeeService.getDetail(id))
                .build();
    }

    @PostMapping("/capsule/bookmark/{id}")
    public BaseResponse<ToggleResponse> toggleCapsuleBookmark(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<ToggleResponse>builder()
                .data(capsuleService.toggleBookmark(id))
                .build();
    }

    @PostMapping("/coffee/bookmark/{id}")
    public BaseResponse<ToggleResponse> toggleCoffeeBookmark(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<ToggleResponse>builder()
                .data(coffeeService.toggleBookmark(id))
                .build();
    }

    @GetMapping("/capsule/review/{id}")
    public BaseResponse<ProductReviewResponse> getCapsuleReviews(@PathVariable @NumberFormat Long id,
                                                                 @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<ProductReviewResponse>builder()
                .data(capsuleReviewService.getCapsuleReviews(id, pageInfoRequest))
                .build();
    }

    @GetMapping("/coffee/review/{id}")
    public BaseResponse<ProductReviewResponse> getCoffeeReviews(@PathVariable @NumberFormat Long id,
                                                                @Valid PageInfoRequest pageInfoRequest) {
        return BaseResponse.<ProductReviewResponse>builder()
                .data(coffeeReviewService.getCoffeeReviews(id, pageInfoRequest))
                .build();
    }

    @GetMapping("/capsule/similar/{id}")
    public BaseResponse<SimilarProductResponse> getSimilarCapsules(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<SimilarProductResponse>builder()
                .data(capsuleService.getSimilarCapsules(id))
                .build();
    }

    @GetMapping("/coffee/similar/{id}")
    public BaseResponse<SimilarProductResponse> getSimilarCoffees(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<SimilarProductResponse>builder()
                .data(coffeeService.getSimilarCoffees(id))
                .build();
    }

    @PostMapping("/capsule/review/{id}")
    public BaseResponse<CreationResponse> createCapsuleReview(@Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<CreationResponse>builder().build();
    }

    @PostMapping("/coffee/review/{id}")
    public BaseResponse<CreationResponse> createCoffeeReview(@Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<CreationResponse>builder().build();
    }

    @PutMapping("/capsule/review/{id}")
    public BaseResponse<Void> editCapsuleReview(@Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<Void>builder().build();
    }

    @PutMapping("/coffee/review/{id}")
    public BaseResponse<Void> editCoffeeReview(@Valid @RequestBody ReviewRequest reviewRequest) {

        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/capsule/review/{id}")
    public BaseResponse<Void> deleteCapsuleReview(@PathVariable @NumberFormat Long id) {

        return BaseResponse.<Void>builder().build();
    }

    @DeleteMapping("/coffee/review/{id}")
    public BaseResponse<Void> deleteCoffeeReview(@PathVariable @NumberFormat Long id) {

        return BaseResponse.<Void>builder().build();
    }

}