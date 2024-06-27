package com.eco.environet.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeseriesFixedExpensesDto {
    private Instant created;
    private String _field;
    private Double _value;
    private String creator_id;
    private String employee;
    private String start_date;
    private String end_date;
    private String description;
}
