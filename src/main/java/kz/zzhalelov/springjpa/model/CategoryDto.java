package kz.zzhalelov.springjpa.model;

import lombok.Builder;

@Builder
public record CategoryDto(int id, String name) {
    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}