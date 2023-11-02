package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;
import ru.practicum.mapper.EndpointMapper;
import ru.practicum.model.Endpoint;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final EndpointMapper endpointMapper;

    @Override
    public Endpoint createStatHit(EndpointDto endpointDto) {
        return statRepository.save(endpointMapper.toEndpoint(endpointDto));
    }

    @Override
    public Collection<StatsDto> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean isUnique) {
        if (uris != null && !uris.isEmpty()) {
            if (isUnique) {
                return statRepository.getUniqueStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            } else {
                return statRepository.getStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            }
        } else {
            if (isUnique) {
                return statRepository.getUniqueBetweenStartAndEndGroupByUri(start, end);
            } else {
                return statRepository.getStatsBetweenStartAndEndGroupByUri(start, end);
            }
        }
    }
}