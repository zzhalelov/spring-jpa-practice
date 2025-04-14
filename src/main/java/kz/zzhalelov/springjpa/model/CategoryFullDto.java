package kz.zzhalelov.springjpa.model;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryFullDto(int id, String name, List<OptionDto> options) {
    public static CategoryFullDto of(Category category) {
        return CategoryFullDto.builder()
                .id(category.getId())
                .name(category.getName())
                .options(
                        category.getOptions().stream()
                                .map(OptionDto::of)
                                .toList()
                )
                .build();
    }

    @Builder
    public record OptionDto(int id, String name) {
        public static OptionDto of(Option option) {
            return OptionDto.builder()
                    .id(option.getId())
                    .name(option.getName())
                    .build();
        }
    }
}