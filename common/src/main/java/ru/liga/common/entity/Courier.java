package ru.liga.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "couriers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courier_seq_gen")
    @SequenceGenerator(name = "courier_seq_gen", sequenceName = "couriers_seq", allocationSize = 1)
    private long id;
    @Column(name = "status")
    private String status;
    @Column(name = "phone")
    private String phone;
    @Column(name = "coordinates")
    private String coordinates;
}