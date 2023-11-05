package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<Collection<CompilationDto>> findCompilations(
            HttpServletRequest request,
            @RequestParam (required = false) Boolean pinned,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) throws ValidationException {
        return new ResponseEntity<>(compilationService.findCompilations(pinned, from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompilationDto> findCompilation(@PathVariable Long id) {
        return new ResponseEntity<>(compilationService.findCompilation(id), HttpStatus.OK);
    }
}
