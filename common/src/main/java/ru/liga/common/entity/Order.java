package ru.liga.common.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "orders_seq", allocationSize = 1)
    private long id;
    @Column(name = "customer_id")
    private long customerId;
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @Column(name = "status")
    private String status;
    @Column(name = "courier_id")
    private Long courierId;
    @Column(name = "timestamp")
    private Date timestamp;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> items;
}
