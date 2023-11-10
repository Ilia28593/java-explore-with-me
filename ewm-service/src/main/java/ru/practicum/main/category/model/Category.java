package ru.practicum.main.category.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
}