package com.startup.Logic.controller.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public final class DateRequest {
    @JsonProperty(value = "first_date")
    private final LocalDate firstDate;
    @JsonProperty(value = "second_date")
    private final LocalDate secondDate;

    public DateRequest(LocalDate firstDate, LocalDate secondDate) {
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public LocalDate getSecondDate() {
        return secondDate;
    }
}
