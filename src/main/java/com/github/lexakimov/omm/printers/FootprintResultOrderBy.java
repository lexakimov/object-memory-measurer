package com.github.lexakimov.omm.printers;

import java.util.Comparator;
import java.util.Map;

/**
 * @author akimov
 * created at: 14.01.2023 22:05
 */
public enum FootprintResultOrderBy {

    TYPE(Map.Entry.comparingByKey()),
    COUNT(Comparator.comparingLong(e -> e.getValue().getCount())),
    SIZE(Comparator.comparingLong(e -> e.getValue().getSize()));

    private final Comparator<Map.Entry<String, FootprintProcessor.Entry>> comparator;

    FootprintResultOrderBy(Comparator<Map.Entry<String, FootprintProcessor.Entry>> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Map.Entry<String, FootprintProcessor.Entry>> getComparator() {
        return comparator;
    }
}
