package ru.practicum.main.compilation.mapper;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;

import java.util.Set;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto()
                .setId(compilation.getId())
                .setEvents(compilation.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList()))
                .setPinned(compilation.isPinned())
                .setTitle(compilation.getTitle());
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, Set<Event> events) {
        return new Compilation()
                .setEvents(events)
                .setPinned(newCompilationDto.isPinned())
                .setTitle(newCompilationDto.getTitle());
    }

    public static Compilation toCompilation(UpdateCompilationRequest updateCompilationRequest, Set<Event> events) {
        return new Compilation()
                .setEvents(events)
                .setPinned(updateCompilationRequest.isPinned());
    }
}