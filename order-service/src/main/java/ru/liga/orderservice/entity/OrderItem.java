package ru.liga.orderservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
@ToString
public class OrderItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_seq_gen")
    @SequenceGenerator(name = "order_items_seq_gen", sequenceName = "order_items_seq", allocationSize = 1)
    private long id;
    @OneToOne
    @JoinColumn(name = "restaurant_menu_item")
    private RestaurantMenuItem restaurantMenuItem;
    @Column(name = "price")
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

}
