package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.repository.StatsRepository;
import ru.practicum.statsDto.EndpointHitDto;
import ru.practicum.statsDto.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public EndpointHitDto createStatHit(EndpointHitDto endpointHitDto) {
        return StatsMapper.toEndpointHitDto(statsRepository.save(StatsMapper.toEndpointHit(endpointHitDto)));
    }

    @Override
    public List<ViewStats> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean unique) {

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("The time of end is not have before start");
        }
        if (uris != null && !uris.isEmpty()) {
            if (unique) {
                return statsRepository.getUniqueStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            } else {
                return statsRepository.getStatsByUrisAndBetweenStartAndEndGroupByUri(start, end, uris);
            }
        } else {
            if (unique) {
                return statsRepository.getUniqueBetweenStartAndEndGroupByUri(start, end);
            } else {
                return statsRepository.getStatsBetweenStartAndEndGroupByUri(start, end);
            }
        }
    }
}