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
            throw new IllegalArgumentException("The time of the end cannot be earlier than the time of the beginning!");
        }

        if (!unique) {
            if (uris == null) {
                return statsRepository.findAllStats(start, end);
            } else {
                return statsRepository.findStats(start, end, uris);
            }
        } else {
            if (uris == null) {
                return statsRepository.findAllUniqueStats(start, end);
            } else {
                return statsRepository.findUniqueStats(start, end, uris);
            }
        }
    }
}