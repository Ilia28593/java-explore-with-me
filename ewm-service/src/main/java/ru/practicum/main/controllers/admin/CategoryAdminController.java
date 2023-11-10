package ru.practicum.main.controllers.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> addCategoryAdmin(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return new ResponseEntity<>(categoryService.addCategoryAdmin(newCategoryDto), CREATED);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<?> updateCategoryAdmin(@Positive @PathVariable Long catId,
                                                 @Valid @RequestBody NewCategoryDto newCategoryDto) {
        return new ResponseEntity<>(categoryService.updateCategoryAdmin(catId, newCategoryDto), OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catId}")
    public void deleteCategoryAdmin(@Positive @PathVariable("catId") Long catId) {
        categoryService.deleteCategoryAdmin(catId);
    }
}