package ru.practicum.main.controllers.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> addCompilationAdmin(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return new ResponseEntity<>(compilationService.addCompilationAdmin(newCompilationDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilationByIdAdmin(@Positive @PathVariable Long compId,
                                                                     @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return new ResponseEntity<>(compilationService.updateCompilationByIdAdmin(compId, updateCompilationRequest),
                HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{compId}")
    public void deleteCompilationByIdAdmin(@Positive @PathVariable("compId") Long compId) {
        compilationService.deleteCompilationByIdAdmin(compId);
    }
}