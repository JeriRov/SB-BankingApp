package com.ipz.mba.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
public class RecentSummaryInfo {
    private int year;
    private String month;
    private long sum;
    private String currency;
    private List<CategorySummary> categories;

    public RecentSummaryInfo(int year, String month, long sum, String currency) {
        this.year = year;
        this.month = month;
        this.sum = sum;
        this.currency = currency;
    }
}