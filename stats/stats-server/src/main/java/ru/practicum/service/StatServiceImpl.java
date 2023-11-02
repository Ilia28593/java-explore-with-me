package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;
import ru.practicum.mapper.EndpointMapper;
import ru.practicum.model.Endpoint;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final EndpointMapper endpointMapper;

    @Override
    public String createStatHit(EndpointDto endpointDto) {
        Endpoint endpoint = endpointMapper.toEndpoint(endpointDto);
        statRepository.save(endpoint);

        return "Информация сохранена";
    }

    @Override
    public Collection<StatsDto> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean isUnique) {
        List<StatsDto> stats;

        if (uris != null && !uris.isEmpty()) {

            if (isUnique) {
                stats = statRepository.getUniqueStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            } else {
                stats = statRepository.getStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            }
        } else {
            if (isUnique) {
                stats = statRepository.getUniqueStatsBetweenStartAndEndGroupByUri(start, end);
            } else {
                stats = statRepository.getStatsBetweenStartAndEndGroupByUri(start, end);
            }
        }

        return stats;
    }
}