package com.ccoins.Bars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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

    @CreatedDate
    @Column(name="start_date")
    private LocalDateTime startDate;
}
