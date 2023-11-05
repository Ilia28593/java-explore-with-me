package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Collection<CategoryDto>> findCategories(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) throws ValidationException {
        return new ResponseEntity<>(categoryService.findCategories(from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findCategory(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findCategory(id), HttpStatus.OK);
    }
}
