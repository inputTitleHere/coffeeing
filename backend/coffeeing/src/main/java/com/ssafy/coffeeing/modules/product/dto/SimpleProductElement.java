package com.ssafy.coffeeing.modules.product.dto;

public record SimpleProductElement(
        Long id,
        String subtitle,
        String title,
        String imageUrl
) {
}
