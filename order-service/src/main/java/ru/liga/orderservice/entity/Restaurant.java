package ru.liga.orderservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurants_seq_gen")
    @SequenceGenerator(name = "restaurants_seq_gen", sequenceName = "restaurants_seq", allocationSize = 1)
    private long id;
//    @Column(name = "name")
//    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private String status;
}
