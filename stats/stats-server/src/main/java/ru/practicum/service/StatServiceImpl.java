package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;
import ru.practicum.mapper.EndpointMapper;
import ru.practicum.model.Endpoint;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

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
        Collection<StatsDto> viewStats = new ArrayList<>();
        if (uris != null) {
            viewStats.addAll(statRepository.countStatByStartEndUriUnique(start, end, uris, isUnique));
        } else {
            viewStats.addAll(statRepository.countStatByStartEndUriUnique(start, end, null, isUnique));
        }

        return viewStats;
    }
}