package ru.liga.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurants_seq_gen")
    @SequenceGenerator(name = "restaurants_seq_gen", sequenceName = "restaurants_seq", allocationSize = 1)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "coordinates")
    private String coordinates;
    @Column(name = "status")
    private String status;
}
