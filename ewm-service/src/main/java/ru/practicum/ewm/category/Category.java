package ru.practicum.ewm.category;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories", schema = "public")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

}
