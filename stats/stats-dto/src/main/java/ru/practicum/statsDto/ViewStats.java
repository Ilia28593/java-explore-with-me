package ru.practicum.statsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(chain = true)
public class ViewStats {

    private String app;
    private String uri;
    private Integer hits;

}
