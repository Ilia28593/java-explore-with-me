package ru.practicum.main.controllers.pub;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategoryPublic(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                               @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(categoryService.getCategoryPublic(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryByIdPublic(@Positive @PathVariable Long catId) {
        return ResponseEntity.ok(categoryService.getCategoryByIdPublic(catId));
    }
}