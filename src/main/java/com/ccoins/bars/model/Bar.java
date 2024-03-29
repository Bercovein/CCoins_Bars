package com.ccoins.bars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.ccoins.bars.utils.DateUtils.AUTO_DATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Bar")
@Table(name = "BARS")
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="menu_link")
    private String menuLink;

    @Column(name="active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name="fk_owner")
    private Long owner;

    @Column(name="start_date",insertable = false, updatable = false,
            columnDefinition = AUTO_DATE)
    private LocalDateTime startDate;

    @Column(name="open_time")
    private LocalTime openTime;

    @Column(name="close_time")
    private LocalTime closeTime;

    @Column(name="location")
    private String location;
}
