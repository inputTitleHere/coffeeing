package com.ssafy.coffeeing.modules.product.controller;

import com.ssafy.coffeeing.modules.product.dto.*;
import com.ssafy.coffeeing.modules.product.service.CapsuleService;
import com.ssafy.coffeeing.modules.product.service.CoffeeService;
import com.ssafy.coffeeing.modules.util.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {

    private final CapsuleService capsuleService;

    private final CoffeeService coffeeService;

    @GetMapping("/capsule/{id}")
    public BaseResponse<CapsuleResponse> getCapsuleDetail(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CapsuleResponse>builder()
                .data(capsuleService.getDetail(id))
                .build();
    }

    @GetMapping("/coffee/{id}")
    public BaseResponse<CoffeeResponse> getCoffeeDetail(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CoffeeResponse>builder()
                .data(coffeeService.getDetail(id))
                .build();
    }

    @PostMapping("/capsule/bookmark/{id}")
    public BaseResponse<Boolean> toggleCapsuleBookmark(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<Boolean>builder()
                .data(capsuleService.toggleBookmark(id))
                .build();
    }

    @PostMapping("/coffee/bookmark/{id}")
    public BaseResponse<Boolean> toggleCoffeeBookmark(@PathVariable @NumberFormat Long id) {
        return BaseResponse.<Boolean>builder()
                .data(coffeeService.toggleBookmark(id))
                .build();
    }

    @GetMapping("/capsule/review/{id}")
    public BaseResponse<CapsuleReviewResponse> getCapsuleReviews(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CapsuleReviewResponse>builder()
                .data(capsuleService.getCapsuleReviews(id))
                .build();
    }

    @GetMapping("/coffee/review/{id}")
    public BaseResponse<CoffeeReviewResponse> getCoffeeReviews(@PathVariable @NumberFormat Long id){
        return BaseResponse.<CoffeeReviewResponse>builder()
                .data(coffeeService.getCoffeeReviews(id))
                .build();
    }

    @GetMapping("/capsule/similar/{id}")
    public BaseResponse<SimilarCapsuleResponse> getSimilarCapsules(@PathVariable @NumberFormat Long id){
        return BaseResponse.<SimilarCapsuleResponse>builder()
                .data(capsuleService.getSimilarCapsules(id))
                .build();
    }

    @GetMapping("/coffee/similar/{id}")
    public BaseResponse<SimilarCoffeeResponse> getSimilarCoffees(@PathVariable @NumberFormat Long id){
        return BaseResponse.<SimilarCoffeeResponse>builder()
                .data(coffeeService.getSimilarCapsules(id))
                .build();
    }
}
