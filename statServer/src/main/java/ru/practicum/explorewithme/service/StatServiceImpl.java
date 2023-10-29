package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.EndpointDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.mapper.StatMapper;
import ru.practicum.explorewithme.repository.StatRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.Constants.DATE_TIME_PATTERN;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final StatMapper statMapper;

    @Override
    public EndpointDto createStatHit(EndpointDto endpointHitDto) {
        return statMapper.endpointToEndPointDto(statRepository
                .save(statMapper.endpointDtoToEndPoint(endpointHitDto))
        );
    }

    @Override
    public Collection<ViewStatsDto> getStatHit(String start, String end, Collection<String> uris, boolean unique) {
        LocalDateTime startDate = getDateFromEncodedString(start);
        LocalDateTime endDate = getDateFromEncodedString(end);
        if (unique) {
            return statRepository.countStatByStartEndUrisUniqueIps(startDate, endDate, uris)
                    .stream().map(statMapper::statsToStatsDto).collect(Collectors.toList());
        } else {
            return statRepository.countStatByStartEndUris(startDate, endDate, uris)
                    .stream().map(statMapper::statsToStatsDto).collect(Collectors.toList());
        }
    }

    private LocalDateTime getDateFromEncodedString(String date) {
        return LocalDateTime.parse(URLDecoder.decode(date, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}
