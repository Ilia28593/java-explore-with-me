package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid NewCategoryDto categoryDto) throws ValidationException {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDto> patchCategory(@PathVariable Long id,
                                                     @RequestBody @Valid NewCategoryDto categoryDto) throws ValidationException {
        return new ResponseEntity<>(categoryService.patchCategory(id, categoryDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws ValidationException {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
