package com.ccoins.Bars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ccoins.Bars.utils.DateUtils.AUTO_DATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "BarTable")
@Table(name = "BAR_TABLES")
public class BarTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="number")
    private Long number;

    @Column(name="active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name="code")
    private String qrCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_bar", referencedColumnName = "id")
    private Bar bar;

    @Column(name="start_date",insertable = false, updatable = false,
            columnDefinition = AUTO_DATE)
    private LocalDateTime startDate;
}
