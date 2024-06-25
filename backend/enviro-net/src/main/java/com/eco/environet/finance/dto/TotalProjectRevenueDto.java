package com.eco.environet.finance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class TotalProjectRevenueDto {
    Page<RevenueDto> content;
    @NotNull(message = "Total Amount is required")
    private double totalAmount = 0;

    public TotalProjectRevenueDto(Page<RevenueDto> content) {
        this.content = content;
        this.totalAmount = calculateTotalAmount(content.getContent());
    }

    private double calculateTotalAmount(List<RevenueDto> content) {
        double result = 0;
        if (content == null || content.isEmpty()) {
            return result;
        } else {
            for (RevenueDto revenueDto : content) {
                result += revenueDto.getAmount();
            }
            return result;
        }
    }
}
