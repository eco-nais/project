package com.eco.environet.finance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionLogEntry {
    private Long version;
    private String timestamp;
    private double totalRevenuesAmount;
    private double totalExpensesAmount;

    public VersionLogEntry(Long version, double totalRevenuesAmount, double totalExpensesAmount){
        this.version = version;
        this.totalExpensesAmount = totalExpensesAmount;
        this.totalRevenuesAmount = totalRevenuesAmount;
        this.timestamp = formatTimestamp(new Timestamp(System.currentTimeMillis()));
    }
    private String formatTimestamp(Timestamp timestamp) {
        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return sdf.format(timestamp);
        } else {
            return null;
        }
    }
}
