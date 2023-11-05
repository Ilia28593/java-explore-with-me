package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<Collection<CompilationDto>> findCompilations(
            HttpServletRequest request,
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) throws ValidationException {
        return new ResponseEntity<>(compilationService.findCompilations(pinned, from, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompilationDto> createCompilation(@RequestBody @Valid NewCompilationDto compilationDto)
            throws ValidationException {
        return new ResponseEntity<>(compilationService.addCompilation(compilationDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompilationDto> patchCompilation(@PathVariable Long id,
                                                           @RequestBody NewCompilationDto compilationDto) {
        return new ResponseEntity<>(compilationService.patchCompilation(id, compilationDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable Long id) {
        compilationService.deleteCompilation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
