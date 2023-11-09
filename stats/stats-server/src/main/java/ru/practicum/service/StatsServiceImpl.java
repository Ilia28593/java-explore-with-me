package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.repository.StatsRepository;
import ru.practicum.statsDto.EndpointHitDto;
import ru.practicum.statsDto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import static ru.practicum.constant.Constants.DATE_FORMAT;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public EndpointHitDto createStatHit(EndpointHitDto endpointHitDto) {
        return StatsMapper.toEndpointHitDto(statsRepository.save(StatsMapper.toEndpointHit(endpointHitDto)));
    }

    @Override
    public List<ViewStats> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean unique) {

        if (end.isBefore(start)) {
            throw new IllegalArgumentException(String.format("Time end %s can not be after start %s",
                    end.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    start.format(DateTimeFormatter.ofPattern(DATE_FORMAT))));
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