package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ResponseEndpointHitDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatService {

    private final StatsRepository statRepository;

    @Transactional
    @Override
    public ResponseEndpointHitDto createStatHit(EndpointHitDto hit) {
        EndpointHit newEndpointHit = statRepository.save(EndpointHitMapper.endpointHitDtoToEndpointHit(hit));
        return EndpointHitMapper.endpointHitToRequestEndpointHitDto(newEndpointHit);
    }

    @Override
    public Collection<ViewStats> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean unique) {
        if (unique) {
            return statRepository.countStatByStartEndUrisUniqueIps(start, end, uris);
        } else {
            return statRepository.countStatByStartEndUris(start, end, uris);
        }
    }
}
