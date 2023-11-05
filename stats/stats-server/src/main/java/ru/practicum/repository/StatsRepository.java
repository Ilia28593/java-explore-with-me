package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.EndpointHit;
import ru.practicum.statsDto.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("SELECT NEW ru.practicum.statsDto.ViewStats(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Endpoint h " +
            "WHERE (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getStatsBetweenStartAndEndGroupByUri(
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("SELECT NEW ru.practicum.statsDto.ViewStats(h.app, h.uri, COUNT(DISTINCT(h.ip))) " +
            "FROM Endpoint h " +
            "WHERE (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(DISTINCT(h.ip)) DESC")
    List<ViewStats> getUniqueBetweenStartAndEndGroupByUri(
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("SELECT NEW ru.practicum.statsDto.ViewStats(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Endpoint h " +
            "WHERE h.uri IN :uris " +
            "AND (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getStatsByUrisAndBetweenStartAndEndGroupByUri(
            LocalDateTime start,
            LocalDateTime end,
            Collection<String> uris
    );

    @Query("SELECT NEW ru.practicum.statsDto.ViewStats(h.app, h.uri, COUNT(DISTINCT(h.ip))) " +
            "FROM Endpoint h " +
            "WHERE h.uri IN :uris " +
            "AND (h.timestamp BETWEEN :start AND :end) " +
            "GROUP BY h.uri, h.app " +
            "ORDER BY COUNT(DISTINCT(h.ip)) DESC")
    List<ViewStats> getUniqueStatsByUrisAndBetweenStartAndEndGroupByUri(
            LocalDateTime start,
            LocalDateTime end,
            Collection<String> uris
    );
}
