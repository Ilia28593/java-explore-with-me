package ru.practicum.explorewithme.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}