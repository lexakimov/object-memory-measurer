package com.github.lexakimov.omm.example;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;
import com.github.lexakimov.omm.footprint.FootprintProcessor;
import com.github.lexakimov.omm.util.Logger;
import lombok.val;
import java.util.HashMap;
import static com.github.lexakimov.omm.footprint.FootprintResultOrderBy.SIZE;
import static com.github.lexakimov.omm.footprint.FootprintResultOrderByDirection.DESC;
import static com.github.lexakimov.omm.footprint.FootprintResultSizeFormat.RAW;

/**
 * Examples of using class {@link ObjectMemoryMeasurer}
 *
 * @author akimov
 * created at: 14.01.2023 21:33
 */
public class ObjectMemoryMeasurerExample {

    public static void main(String[] args) {

        // creation of the investigated object
        val objectToAnalyze = new HashMap<>();
        objectToAnalyze.put("key1", new Object());
        objectToAnalyze.put("key2", 123);
        objectToAnalyze.put("key3", 123L);
        objectToAnalyze.put("key4", true);
        objectToAnalyze.put("key5", "str");

        // print footprint to console
        ObjectMemoryMeasurer.analyze(objectToAnalyze).getFootprint().printOut();

        System.out.println();
        System.out.println();

        // or you can customize aggregation and output
        val processor = new FootprintProcessor();
        processor.registerStopWord("com.github.lexakimov.omm.example.SomeClass");
        processor.registerStopWordRegexp("org\\.apache.*");
        processor.setLinePrinter(Logger::info);
        processor.setSizeFormat(RAW);
        processor.setOrderBy(SIZE, DESC);
        processor.setLimit(5);

        ObjectMemoryMeasurer.analyze(objectToAnalyze).getFootprint(processor).printOut();
    }
}
