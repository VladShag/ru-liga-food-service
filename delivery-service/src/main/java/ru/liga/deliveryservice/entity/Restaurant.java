package ru.liga.deliveryservice.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "restaurants")
@ToString
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

}
