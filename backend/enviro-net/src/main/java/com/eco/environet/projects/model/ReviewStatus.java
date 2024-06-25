package com.eco.environet.projects.model;

public enum ReviewStatus {
    REQUESTED,
    IN_PROGRESS,
    APPROVED,
    REJECTED;

    @Override
    public String toString() {
        return switch (this) {
            case REQUESTED -> "Requested";
            case IN_PROGRESS -> "In Progress";
            case APPROVED -> "Approved";
            case REJECTED -> "Rejected";
        };
    }
}
