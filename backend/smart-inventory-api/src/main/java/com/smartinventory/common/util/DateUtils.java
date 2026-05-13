package com.smartinventory.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateUtils {
    private DateUtils() {
    }

    public static LocalDateTime startOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(1).atStartOfDay();
    }
}
