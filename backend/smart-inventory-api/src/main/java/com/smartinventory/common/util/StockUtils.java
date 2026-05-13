package com.smartinventory.common.util;

public final class StockUtils {
    private StockUtils() {
    }

    public static boolean isLowStock(int current, int minimum) {
        return current <= minimum;
    }

    public static boolean isCritical(int current) {
        return current <= 0;
    }

    public static boolean isOverstock(int current, int maximum) {
        return current >= maximum;
    }
}
