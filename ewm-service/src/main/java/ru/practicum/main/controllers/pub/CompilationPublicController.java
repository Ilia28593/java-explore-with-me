package ru.practicum.main.controllers.pub;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getCompilationsPublic(@RequestParam(required = false, name = "pinned") Boolean pinned,
                                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get request received compilations.");
        return ResponseEntity.ok(compilationService.getCompilationsPublic(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilationByIdPublic(@Positive @PathVariable Long compId) {
        log.info("Get request received compilations by id.");
        return ResponseEntity.ok(compilationService.getCompilationByIdPublic(compId));
    }
}