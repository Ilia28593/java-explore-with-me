package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.StatsDto;
import ru.practicum.model.Endpoint;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface StatRepository extends JpaRepository<Endpoint, Integer> {

    @Query("SELECT NEW ru.practicum.StatsDto(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Endpoint h " +
            "WHERE (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(h.ip) DESC")
    Collection<StatsDto> getStatsBetweenStartAndEndGroupByUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT NEW ru.practicum.StatsDto(h.app, h.uri, COUNT(DISTINCT(h.ip))) " +
            "FROM Endpoint h " +
            "WHERE (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(DISTINCT(h.ip)) DESC")
    Collection<StatsDto> getUniqueStatsBetweenStartAndEndGroupByUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT NEW ru.practicum.StatsDto(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Endpoint h " +
            "WHERE h.uri IN :uris " +
            "AND (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(h.ip) DESC")
    Collection<StatsDto> getStatsByUrisAndBetweenStartAndEndGroupByUri(LocalDateTime start, LocalDateTime end, Collection<String> uris);

    @Query("SELECT NEW ru.practicum.StatsDto(h.app, h.uri, COUNT(DISTINCT(h.ip))) " +
            "FROM Endpoint h " +
            "WHERE h.uri IN :uris " +
            "AND (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(DISTINCT(h.ip)) DESC")
    Collection<StatsDto> getUniqueStatsByUrisAndBetweenStartAndEndGroupByUri(LocalDateTime start, LocalDateTime end, Collection<String> uris);
}