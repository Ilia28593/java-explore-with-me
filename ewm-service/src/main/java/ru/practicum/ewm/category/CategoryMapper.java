package ru.practicum.ewm.category;

import org.mapstruct.Mapper;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDto category);

    Category toCategory(NewCategoryDto category);

    Collection<Category> toCategory(Collection<CategoryDto> category);

    CategoryDto toCategoryDto(Category categories);

    Collection<CategoryDto> toCategoryDto(Collection<Category> categories);
}
