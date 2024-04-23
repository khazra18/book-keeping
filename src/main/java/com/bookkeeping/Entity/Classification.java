package com.bookkeeping.Entity;

import org.springframework.validation.annotation.Validated;

@Validated
public enum Classification {
    FICTION, COMIC, ADVENTURE, TRAVEL, HEALTH, MYSTERY;
}
