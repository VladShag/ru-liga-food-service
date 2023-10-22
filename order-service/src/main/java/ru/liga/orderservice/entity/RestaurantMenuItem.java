package ru.liga.orderservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_menu_items_seq_gen")
    @SequenceGenerator(name = "restaurant_menu_items_seq_gen", sequenceName = "restaurant_menu_items_seq", allocationSize = 1)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "image")
    private String image;
    @Column(name = "description")
    private String description;

}
