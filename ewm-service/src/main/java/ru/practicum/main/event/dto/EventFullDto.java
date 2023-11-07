package ru.practicum.main.event.dto;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.user.dto.UserShortDto;
@Getter
@Setter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private Boolean paid;
    private String eventDate;
    private UserShortDto initiator;
    private Long confirmedRequests;
    private String description;
    private Integer participantLimit;
    private String state;
    private String createdOn;
    private LocationDto location;
    private Boolean requestModeration;
    private String publishedOn;
    private Long views;
}