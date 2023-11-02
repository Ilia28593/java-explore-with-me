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
    @Query("select new ru.practicum.StatsDto(h.app, h.uri, " +
            "(case when :unique = true then count(distinct(h.ip)) else count(h.id) end))" +
            "from EndpointHit as h " +
            "where (h.timestamp >= :start or cast(:start as date) is null) " +
            "and (h.timestamp <= :end or cast(:end as date) is null) " +
            "and (h.uri in :uris or :uris is null ) " +
            "group by h.app, h.uri " +
            "order by (case when :unique = true then count(distinct(h.ip)) else count(h.id) end) desc")
    Collection<StatsDto> countStatByStartEndUriUnique(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean unique);

}