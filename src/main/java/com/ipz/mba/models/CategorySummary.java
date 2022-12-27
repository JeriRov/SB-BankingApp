package com.ipz.mba.models;

import com.ipz.mba.entities.CategoryEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class CategorySummary {
    private String category;
    private Long totalSpendPerMonth;
    private String currency;
    private Long totalSum;

    public CategorySummary(CategoryEntity category, Long totalSpendPerMonth) {
        this.category = category.getName();
        this.totalSpendPerMonth = totalSpendPerMonth;
    }
}