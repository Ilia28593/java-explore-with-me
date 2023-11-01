package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;
import ru.practicum.mapper.EndpointMapper;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final EndpointMapper endpointMapper;

    public String createStatHit(EndpointDto endpointDto) {
        statRepository.save(endpointMapper.fromDto(endpointDto));
        return "Информация сохранена";
    }

    public Collection<StatsDto> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean isUnique) {
        if (Objects.nonNull(uris) && !uris.isEmpty()) {
            if (isUnique) {
                return statRepository.getUniqueStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            } else {
                return statRepository.getStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            }
        } else {
            if (isUnique) {
                return statRepository.getUniqueStatsBetweenStartAndEndGroupByUri(start, end);
            } else {
                return statRepository.getStatsBetweenStartAndEndGroupByUri(start, end);
            }
        }
    }
}