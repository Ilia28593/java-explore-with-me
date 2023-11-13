package ru.practicum.main.controllers.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> addCompilationAdmin(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Post request a new compilation.");
        return new ResponseEntity<>(compilationService.addCompilationAdmin(newCompilationDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilationByIdAdmin(@Positive @PathVariable Long compId,
                                                                     @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("Patch request update compilation.");
        return new ResponseEntity<>(compilationService.updateCompilationByIdAdmin(compId, updateCompilationRequest),
                HttpStatus.OK);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilationByIdAdmin(@Positive @PathVariable("compId") Long compId) {
        log.info("Delete request compilation.");
        compilationService.deleteCompilationByIdAdmin(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}