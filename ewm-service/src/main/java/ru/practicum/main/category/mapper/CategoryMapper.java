package ru.practicum.main.category.mapper;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.model.Category;

public class CategoryMapper {

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category()
                .setName(newCategoryDto.getName());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto()
                .setId(category.getId())
                .setName(category.getName());
    }
}