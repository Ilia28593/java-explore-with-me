package ru.practicum.main.location.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.location.model.Location;
import ru.practicum.main.location.repository.LocationRepository;

@Slf4j
@Service
@AllArgsConstructor
public class LocationServiceImpl {
    private LocationRepository locationRepository;

    public Location save(Location location) {
        return locationRepository.save(location);
    }
}
