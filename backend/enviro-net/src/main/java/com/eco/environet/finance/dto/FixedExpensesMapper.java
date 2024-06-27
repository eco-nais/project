package com.eco.environet.finance.dto;

public class FixedExpensesMapper {

    public static TimeseriesFixedExpensesDto toTimeseriesFixedExpensesDto(FixedExpensesDto fixedExpensesDto) {
        return TimeseriesFixedExpensesDto.builder()
                .created(fixedExpensesDto.getCreatedOn().toInstant())
                ._field(fixedExpensesDto.getType())
                ._value(fixedExpensesDto.getAmount())
                .creator_id(fixedExpensesDto.getCreator().getId().toString())
                .employee(fixedExpensesDto.getEmployee() != null ? fixedExpensesDto.getEmployee().getId().toString() : null)
                .start_date(fixedExpensesDto.getPeriod().getStartDate().toString()+"T00:00:00.000Z")
                .end_date(fixedExpensesDto.getPeriod().getEndDate().toString()+"T00:00:00.000Z")
                .description(fixedExpensesDto.getDescription())
                .build();
    }
}