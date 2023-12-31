package ru.practicum.main.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.category.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getById(Long catId);

    void deleteCategoryById(Long catId);

    Category findCategoryById(Long catId);

}