package ru.practicum.main.category.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.DuplicateNameException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidationCategoryException;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CategoryDto addCategoryAdmin(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toCategory(newCategoryDto);
        try {
            return CategoryMapper.toCategoryDto(categoryRepository.save(category));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateNameException("Duplicate category name");
        }
    }

    @Transactional
    @Override
    public CategoryDto updateCategoryAdmin(Long catId, NewCategoryDto newCategoryDto) {
        getCategory(catId);
        Category newCategory = CategoryMapper.toCategory(newCategoryDto);
        newCategory.setId(catId);
        CategoryDto categoryDto;
        try {
            categoryDto = CategoryMapper.toCategoryDto(categoryRepository.saveAndFlush(newCategory));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateNameException("Duplicate category name");
        }
        return categoryDto;
    }

    @Transactional
    @Override
    public void deleteCategoryAdmin(Long catId) {
        getCategory(catId);
        getCategory(catId);
        if (eventRepository.findFirstByCategoryId(catId) != null) {
            throw new ValidationCategoryException("Category has not been deleted.");
        }
        categoryRepository.deleteCategoryById(catId);
    }


    @Transactional
    @Override
    public List<CategoryDto> getCategoryPublic(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CategoryDto getCategoryByIdPublic(Long catId) {
        return CategoryMapper.toCategoryDto(getCategory(catId));
    }

    private Category getCategory(Long categoryId) {
        Category category = categoryRepository.getById(categoryId);
        if (category == null) {
            throw new NotFoundException("Category not found.");
        }
        return category;
    }
}