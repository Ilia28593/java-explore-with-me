package ru.practicum.main.category.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @Size(min = 1, max = 50)
    private String name;
}