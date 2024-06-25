package com.eco.environet.finance.model;

import java.util.Arrays;
import java.util.List;

public enum RevenueType {
    PROJECT_REVENUE,
    PROJECT_DONATION,
    LECTURE_REVENUE,
    LECTURE_DONATION,
    DONATION;

    public static List<RevenueType> getAllDonations(){
        return Arrays.asList(DONATION, PROJECT_DONATION, LECTURE_DONATION);
    }
    public static List<RevenueType> getAllProjectTypes(){
        return Arrays.asList(PROJECT_DONATION, PROJECT_REVENUE);
    }
    public static List<RevenueType> getAllLectureTypes(){
        return Arrays.asList(LECTURE_DONATION, LECTURE_REVENUE);
    }
}