package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.PaginationHelper;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.category.CategoryRepository;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.Collection;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto addCategory(final NewCategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        Category createdCategory;
        try {
            createdCategory = categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Name is not unique");
        }
        return categoryMapper.toCategoryDto(createdCategory);
    }

    @Override
    public Collection<CategoryDto> findCategories(Integer from, Integer size) {

        Pageable pageable = PaginationHelper.makePageable(from, size);

        return categoryMapper.toCategoryDto(categoryRepository.findAll(pageable).getContent());
    }

    @Override
    public CategoryDto patchCategory(Long id, NewCategoryDto categoryDto) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryToUpdate.setName(categoryDto.getName());
        Category updatedCategory;
        try {
            updatedCategory = categoryRepository.save(categoryToUpdate);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Name is not unique");
        }
        return categoryMapper.toCategoryDto(updatedCategory);

    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        if (eventRepository.countByCategoryId(id) > 0) {
            throw new ConflictException("Cannot delete category with linked events");
        }
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto findCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        return categoryMapper.toCategoryDto(category);
    }
}
