package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.mapper.StatMapper;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;
import ru.practicum.explorewithme.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final StatMapper statMapper;

    @Override
    public EndpointHit createStatHit(EndpointHitDto hit) {
        return statRepository.save(statMapper.endpointHitDtoToEndPointHit(hit));
    }

    @Override
    public Collection<ViewStatsDto> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean unique) {
        Collection<ViewStats> viewStats;
        if (unique) {
            viewStats = statRepository.countStatByStartEndUrisUniqueIps(start, end, uris);
        } else {
            viewStats = statRepository.countStatByStartEndUris(start, end, uris);
        }
        return viewStats.stream().map(statMapper::viewStatsToViewStatsDto).collect(Collectors.toList());
    }
}