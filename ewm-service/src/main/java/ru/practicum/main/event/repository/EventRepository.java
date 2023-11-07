package ru.practicum.main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> getEventsByCategoryIdIn(List<Long> categories, Pageable pageable);

    List<Event> getEventsByStateIn(List<State> states, Pageable pageable);

    List<Event> getEventsByInitiatorIdIn(List<Long> users, Pageable pageable);

    List<Event> getEventsByCategoryIdInAndStateIn(List<Long> categories, List<State> states, Pageable pageable);

    List<Event> getEventsByInitiatorIdInAndStateIn(List<Long> users, List<State> states, Pageable pageable);

    List<Event> getEventsByInitiatorIdInAndCategoryIdIn(List<Long> users, List<Long> categories, Pageable pageable);

    List<Event> getEventsByInitiatorIdInAndStateInAndCategoryIdIn(List<Long> users, List<State> states, List<Long> categories, Pageable pageable);

    List<Event> getEventsByEventDateAfterAndEventDateBefore(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Event getEventByIdAndState(Long eventId, State state);

    List<Event> getEventsByCategoryIdInAndEventDateAfterAndEventDateBefore(List<Long> categories, LocalDateTime start,
                                                                           LocalDateTime end, Pageable pageable);

    List<Event> getEventsByStateInAndEventDateAfterAndEventDateBefore(List<State> states, LocalDateTime start,
                                                                      LocalDateTime end, Pageable pageable);

    List<Event> getEventsByInitiatorIdInAndEventDateAfterAndEventDateBefore(List<Long> users, LocalDateTime start,
                                                                            LocalDateTime end, Pageable pageable);

    List<Event> getEventsByStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<State> states, List<Long> categories,
                                                                                     LocalDateTime start, LocalDateTime end,
                                                                                     Pageable pageable);

    List<Event> getEventsByInitiatorIdInAndStateInAndEventDateAfterAndEventDateBefore(List<Long> users, List<State> states,
                                                                                      LocalDateTime start, LocalDateTime end,
                                                                                      Pageable pageable);

    List<Event> getEventsByInitiatorIdInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Long> users, List<Long> categories,
                                                                                           LocalDateTime start, LocalDateTime end,
                                                                                           Pageable pageable);

    List<Event> getEventsByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(List<Long> users,
                                                                                                     List<State> stateEnum,
                                                                                                     List<Long> categories,
                                                                                                     LocalDateTime start,
                                                                                                     LocalDateTime end,
                                                                                                     Pageable pageable);

    List<Event> getEventsByInitiatorId(Long userId, Pageable pageable);

    Event findFirstByCategoryId(Long catId);

    Optional<Event> getEventsByIdAndInitiatorId(Long eventId, Long userId);

    Event getEventsById(Long eventId);

    Set<Event> getEventsByIdIn(List<Long> events);

    List<Event> getEventsByInitiatorId(Long userId);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailableCategoryText(String state, List<Long> category, boolean paid,
                                                                    LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailableCategory(String state, List<Long> category, boolean paid,
                                                                LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailableText(String state, boolean paid, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailable(String state, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date > ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateCategoryText(String state, List<Long> category, boolean paid, LocalDateTime time,
                                                           String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date > ?4 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateCategory(String state, List<Long> category, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateText(String state, boolean paid, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date > ?3 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDate(String state, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailableCategoryText(String state, List<Long> category, boolean paid, LocalDateTime time, String text,
                                                                Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailableCategory(String state, List<Long> category, boolean paid, LocalDateTime time,
                                                            Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailableText(String state,
                                                        boolean paid,
                                                        LocalDateTime time,
                                                        String text,
                                                        Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailable(String state, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date > ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsCategoryText(String state, List<Long> category, boolean paid, LocalDateTime time,
                                                       String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date > ?4 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsCategory(String state, List<Long> category, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsText(String state, boolean paid, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date > ?3 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViews(String state, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailableCategoryText(String state, List<Long> category, boolean paid, LocalDateTime time, String text,
                                                       Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailableCategory(String state, List<Long> category, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailableText(String state, boolean paid, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?4 ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailable(String state, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date > ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) ", nativeQuery = true)
    List<Event> getEventsNoPeriodCategoryText(String state, List<Long> category, boolean paid, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 and e.event_date > ?4 ", nativeQuery = true)
    List<Event> getEventsNoPeriodCategory(String state, List<Long> category, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) ", nativeQuery = true)
    List<Event> getEventsWithOutPeriodText(String state, boolean paid, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date > ?3 ", nativeQuery = true)
    List<Event> getEventsWithOutPeriod(String state, boolean paid, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "and upper(e.annotation) like upper(?6) or upper(e.description) like upper(?6) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailableText(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                                    LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailable(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                                LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailableText(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                                    String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >=  ?3 and e.event_date <= ?4 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailable(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                                Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "and upper(e.annotation) like upper(?6) or upper(e.description) like upper(?6) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateCategoryText(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                                   LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateCategory(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                               LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date >= ?3 and e.event_date < = ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateText(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                           String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDate(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                       Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "and upper(e.annotation) like upper(?6) or upper(e.description) like upper(?6) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsAvailableText(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                                LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsPeriodSortViewsAvailableCategory(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                                          LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsAvailableText(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                                String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsAvailable(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                            Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "and upper(e.annotation) like upper(?6) or upper(e.description) like upper(?6) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsCategoryText(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                               LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsCategory(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                           LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsText(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                       String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViews(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "and upper(e.annotation) like upper(?6) or upper(e.description) like upper(?6) ", nativeQuery = true)
    List<Event> getEventsAvailableCategoryText(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                               LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?4 and e.event_date <= ?5 ", nativeQuery = true)
    List<Event> getEventsAvailableCategory(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                           LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) ", nativeQuery = true)
    List<Event> getEventsAvailableText(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd,
                                       String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?4 and e.event_date <= ?5 ", nativeQuery = true)
    List<Event> getEventsAvailable(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date >= ?4 and e.event_date <= ?5 " +
            "and upper(e.annotation) like upper(?6) or upper(e.description) like upper(?6) ", nativeQuery = true)
    List<Event> getEventsCategoryText(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                      LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.paid=?3 " +
            "and e.event_date >= ?4 and e.event_date <= ?5 ", nativeQuery = true)
    List<Event> getEventsCategory(String state, List<Long> category, boolean paid, LocalDateTime timeStart,
                                  LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) ", nativeQuery = true)
    List<Event> getEventsText(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd, String text,
                              Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 ", nativeQuery = true)
    List<Event> getEventsPeriod(String state, boolean paid, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailableCategoryText(String state, List<Long> category, LocalDateTime time,
                                                                    String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailableCategory(String state, List<Long> category, LocalDateTime time,
                                                                Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?2 " +
            "and upper(e.annotation) like upper(?3) or upper(e.description) like upper(?3) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailableText(String state,
                                                            LocalDateTime time,
                                                            String text,
                                                            Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?2 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateAvailable(String state, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateCategoryText(String state, List<Long> category, LocalDateTime time,
                                                           String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date > ?3 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateCategory(String state, List<Long> category, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date > ?2 " +
            "and upper(e.annotation) like upper(?3) or upper(e.description) like upper(?3) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDateText(String state, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date > ?2 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortEventDate(String state, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailableCategoryText(String state, List<Long> category, LocalDateTime time,
                                                                String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailableCategory(String state, List<Long> category, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?2 " +
            "and upper(e.annotation) like upper(?3) or upper(e.description) like upper(?3) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailableText(String state, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?2 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsAvailable(String state, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsCategoryText(String state, List<Long> category, LocalDateTime time, String text,
                                                       Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date > ?3 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsCategory(String state, List<Long> category, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date > ?2 " +
            "and upper(e.annotation) like upper(?3) or upper(e.description) like upper(?3) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViewsText(String state, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date > ?2 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsNoPeriodSortViews(String state, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailableCategoryText(String state, List<Long> category, LocalDateTime time, String text,
                                                       Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?3 ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailableCategory(String state, List<Long> category, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?2 " +
            "and upper(e.annotation) like upper(?3) or upper(e.description) like upper(?3) ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailableText(String state, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date > ?2 ", nativeQuery = true)
    List<Event> getEventsNoPeriodAvailable(String state, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date > ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) ", nativeQuery = true)
    List<Event> getEventsNoPeriodCategoryText(String state, List<Long> category, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date > ?3 ", nativeQuery = true)
    List<Event> getEventsNoPeriodCategory(String state, List<Long> category, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date > ?2 " +
            "and upper(e.annotation) like upper(?3) or upper(e.description) like upper(?3) ", nativeQuery = true)
    List<Event> getEventsWithOutPeriodText(String state, LocalDateTime time, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date > ?2 ", nativeQuery = true)
    List<Event> getEventsWithOutPeriod(String state, LocalDateTime time, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailableText(String state, List<Long> category, LocalDateTime timeStart,
                                                    LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailable(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd,
                                                Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailableText(String state, LocalDateTime timeStart, LocalDateTime timeEnd, String text,
                                                    Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >=  ?2 and e.event_date <= ?3 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateAvailable(String state, LocalDateTime timeStart, LocalDateTime timeEnd,
                                                Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateCategoryText(String state, List<Long> category, LocalDateTime timeStart,
                                                   LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateCategory(String state, List<Long> category, LocalDateTime timeStart,
                                               LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date >= ?2 and e.event_date < = ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDateText(String state, LocalDateTime timeStart, LocalDateTime timeEnd, String text,
                                           Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "order by e.event_date desc ", nativeQuery = true)
    List<Event> getEventsSortEventDate(String state, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsAvailableText(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd,
                                                String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsPeriodSortViewsAvailableCategory(String state, List<Long> category, LocalDateTime timeStart,
                                                          LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsAvailableText(String state, LocalDateTime timeStart, LocalDateTime timeEnd,
                                                String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsAvailable(String state, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsCategoryText(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd,
                                               String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsCategory(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd,
                                           Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViewsText(String state, LocalDateTime timeStart, LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "order by e.views desc ", nativeQuery = true)
    List<Event> getEventsSortViews(String state, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) ", nativeQuery = true)
    List<Event> getEventsAvailableCategoryText(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd,
                                               String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 ", nativeQuery = true)
    List<Event> getEventsAvailableCategory(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) ", nativeQuery = true)
    List<Event> getEventsAvailableText(String state, LocalDateTime timeStart, LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.paid=?2 " +
            "and e.participant_limit = 0 or e.participant_limit > e.confirmed_requests " +
            "and e.event_date >= ?3 and e.event_date <= ?4 ", nativeQuery = true)
    List<Event> getEventsAvailable(String state, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 " +
            "and upper(e.annotation) like upper(?5) or upper(e.description) like upper(?5) ", nativeQuery = true)
    List<Event> getEventsCategoryText(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd,
                                      String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.category_id in ?2 " +
            "and e.event_date >= ?3 and e.event_date <= ?4 ", nativeQuery = true)
    List<Event> getEventsCategory(String state, List<Long> category, LocalDateTime timeStart, LocalDateTime timeEnd,
                                  Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date >= ?2 and e.event_date <= ?3 " +
            "and upper(e.annotation) like upper(?4) or upper(e.description) like upper(?4) ", nativeQuery = true)
    List<Event> getEventsText(String state, LocalDateTime timeStart, LocalDateTime timeEnd, String text, Pageable pageable);

    @Query(value = "select * from events as e " +
            "where e.state=?1 " +
            "and e.event_date >= ?2 and e.event_date <= ?3 ", nativeQuery = true)
    List<Event> getEventsPeriod(String state, LocalDateTime timeStart, LocalDateTime timeEnd, Pageable pageable);
}