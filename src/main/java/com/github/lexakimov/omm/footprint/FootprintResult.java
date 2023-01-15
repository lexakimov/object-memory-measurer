package com.github.lexakimov.omm.footprint;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;
import lombok.val;
import lombok.var;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author akimov
 * created at: 14.01.2023 21:20
 */
public class FootprintResult {

    private final ObjectMemoryMeasurer measurer;
    private final FootprintProcessor footprintProcessor;
    private final Map<String, FootprintProcessor.Entry> resultMap;

    public FootprintResult(ObjectMemoryMeasurer measurer, FootprintProcessor footprintProcessor) {
        this.measurer = measurer;
        this.footprintProcessor = footprintProcessor;
        this.resultMap = footprintProcessor.process(measurer);
    }

    public void printOut() {
        val linePrinter = footprintProcessor.getLinePrinter();
        val sizeFormattingFunction = footprintProcessor.getSizeFormat().getFormattingFunction();

        val graphRoot = measurer.getGraphRoot();
        val typeString = graphRoot.getTypeString();
        linePrinter.accept("memory footprint of " + typeString + " instance:");

        String format = "%10s %10s %-60s";

        long totalSize = 0;
        long totalCount = 0;

        linePrinter.accept(String.format(format, "Count", "Size", "Type"));

        val comparator = footprintProcessor.getOrderingComparator();


        var stream = resultMap.entrySet().stream();
        if (comparator != null) {
            stream = stream.sorted(comparator);
        }

        val limit = footprintProcessor.getLimit();
        if (limit > 0) {
            stream = stream.limit(limit);
        }

        for (Map.Entry<String, FootprintProcessor.Entry> entry : stream.collect(Collectors.toList())) {
            String type = entry.getKey();
            val result = entry.getValue();
            totalSize = Math.addExact(totalSize, result.getSize());
            totalCount = Math.addExact(totalCount, result.getCount());
            linePrinter.accept(String.format(
                    format,
                    result.getCount(),
                    sizeFormattingFunction.apply(result.getSize()),
                    type
            ));
        }

        linePrinter.accept("----------------------------------------------------------------------------------");
        linePrinter.accept(String.format(format, totalCount, sizeFormattingFunction.apply(totalSize), "Total"));
    }
}
