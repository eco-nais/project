package com.eco.environet.finance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Builder(builderMethodName = "revenueBuilder")
@Table(name="revenues", schema = "finance")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "created_on", nullable = false)
    private Timestamp createdOn = Timestamp.from(Instant.now());

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RevenueType type = RevenueType.DONATION;

    @Min(value = 0, message = "Amount must be greater than 0")
    @Column(name = "amount", nullable = false)
    private double amount = 100;
}
