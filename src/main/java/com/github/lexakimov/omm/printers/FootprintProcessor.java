package com.github.lexakimov.omm.printers;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;
import com.github.lexakimov.omm.types.HasNestedVariables;
import com.github.lexakimov.omm.types.Variable;
import lombok.EqualsAndHashCode;
import lombok.val;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import static com.github.lexakimov.omm.printers.FootprintResultOrderByDirection.ASC;

/**
 * Process traversing result to plain list.
 *
 * @author akimov
 * created at: 14.01.2023 18:51
 */
public class FootprintProcessor {

    private final Set<String> stopRegExpressions = new HashSet<>();
    private Consumer<Object> linePrinter = System.out::println;
    private Comparator<Map.Entry<String, Entry>> comparator;
    private int limit = -1;

    public void registerStopWord(String stopWord) {
        Objects.requireNonNull(stopWord);
        stopRegExpressions.add(Pattern.quote(stopWord));
    }

    public void registerStopWordRegexp(String stopWordRegexp) {
        Objects.requireNonNull(stopWordRegexp);
        stopRegExpressions.add(stopWordRegexp);
    }

    int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be greater than zero");
        }
        this.limit = limit;
    }

    Comparator<Map.Entry<String, Entry>> getOrderingComparator() {
        return comparator;
    }

    public void setOrderBy(FootprintResultOrderBy orderBy, FootprintResultOrderByDirection direction) {
        Objects.requireNonNull(orderBy);
        Objects.requireNonNull(direction);
        comparator = (direction == ASC) ? orderBy.getComparator() : orderBy.getComparator().reversed();
    }

    Consumer<Object> getLinePrinter() {
        return linePrinter;
    }

    public void setLinePrinter(Consumer<Object> linePrinter) {
        Objects.requireNonNull(linePrinter);
        this.linePrinter = linePrinter;
    }

    private boolean shouldStop(String typeString) {
        return stopRegExpressions.stream().anyMatch(typeString::matches);
    }

    Map<String, Entry> process(ObjectMemoryMeasurer measurer) {
        val graphRoot = measurer.getGraphRoot();

        val resultByType = new TreeMap<String, Entry>();

        Deque<Variable> stack = new ArrayDeque<>();
        stack.push(graphRoot);
        while (!stack.isEmpty()) {
            val variable = stack.pop();
            val typeString = variable.getTypeString();

            resultByType.compute(typeString, (k, v) -> {
                if (v == null) {
                    return new Entry(1, 0);
                }
                v.incrementCount();
                return v;
            });

            if (variable instanceof HasNestedVariables) {
                if (shouldStop(typeString)) {
                    resultByType.compute(typeString, (k, v) -> {
                        if (v == null) {
                            v = new Entry(1, 0);
                        }
                        v.addSize(variable.getSizeInBytes());
                        return v;
                    });
                } else {
                    val variableWithNested = (HasNestedVariables) variable;
                    resultByType.compute(typeString, (k, v) -> {
                        if (v == null) {
                            v = new Entry(1, 0);
                        }
                        v.addSize(variable.getSizeInBytes() - variableWithNested.getNestedVariablesSizeInBytes());
                        return v;
                    });
                    variableWithNested.getNestedVariables().forEach(stack::push);
                }
            } else {
                resultByType.compute(typeString, (k, v) -> {
                    if (v == null) {
                        v = new Entry(1, 0);
                    }
                    v.addSize(variable.getSizeInBytes());
                    return v;
                });
            }
        }

        return resultByType;
    }

    @EqualsAndHashCode
    public static class Entry {
        /**
         * Total count ob object instances.
         */
        private long count;

        /**
         * Summary size of given type's objects.
         */
        private long size;

        public Entry(long count, long size) {
            this.count = count;
            this.size = size;
        }

        public void incrementCount() {
            count++;
        }

        public void addSize(long size) {
            this.size = Math.addExact(this.size, size);
        }

        public long getCount() {
            return count;
        }

        public long getSize() {
            return size;
        }
    }

}
