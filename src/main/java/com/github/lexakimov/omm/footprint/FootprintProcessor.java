package com.github.lexakimov.omm.footprint;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;
import com.github.lexakimov.omm.types.HasNestedVariables;
import com.github.lexakimov.omm.types.Variable;
import lombok.EqualsAndHashCode;
import lombok.val;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import static com.github.lexakimov.omm.footprint.FootprintResultOrderByDirection.ASC;

/**
 * Process traversing result to plain list.
 *
 * @author akimov
 * created at: 14.01.2023 18:51
 */
public class FootprintProcessor {

    private final Set<Pattern> stopRegExpressions = new HashSet<>();
    private Comparator<Map.Entry<String, Entry>> comparator;
    private Consumer<Object> linePrinter = System.out::println;
    private FootprintResultSizeFormat sizeFormat = FootprintResultSizeFormat.BINARY;
    private int limit = -1;

    /**
     * Add a new graph traversal stop-word.
     *
     * @param stopWord stop-word string.
     */
    public void registerStopWord(String stopWord) {
        Objects.requireNonNull(stopWord);
        stopRegExpressions.add(Pattern.compile(stopWord, Pattern.LITERAL));
    }

    /**
     * Add a new graph traversal stop-word as regular expression.
     *
     * @param stopWordRegexp stop-word regular expression.
     */
    public void registerStopWordRegexp(String stopWordRegexp) {
        Objects.requireNonNull(stopWordRegexp);
        stopRegExpressions.add(Pattern.compile(stopWordRegexp));
    }

    /**
     * Set custom ordering column and direction. By default, the list will be sorted in order of appearance.
     *
     * @param orderBy   ordering column.
     * @param direction direction of order.
     */
    public void setOrderBy(FootprintResultOrderBy orderBy, FootprintResultOrderByDirection direction) {
        Objects.requireNonNull(orderBy);
        Objects.requireNonNull(direction);
        comparator = (direction == ASC) ? orderBy.getComparator() : orderBy.getComparator().reversed();
    }

    /**
     * Set custom line printing procedure.
     *
     * @param linePrinter line printing procedure.
     */
    public void setLinePrinter(Consumer<Object> linePrinter) {
        Objects.requireNonNull(linePrinter);
        this.linePrinter = linePrinter;
    }

    /**
     * Set the output format of size number.
     *
     * @param sizeFormat format enum.
     */
    public void setSizeFormat(FootprintResultSizeFormat sizeFormat) {
        Objects.requireNonNull(sizeFormat);
        this.sizeFormat = sizeFormat;
    }

    /**
     * Set object rows limit.
     *
     * @param limit positive number of rows.
     */
    public void setLimit(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be greater than zero");
        }
        this.limit = limit;
    }

    Comparator<Map.Entry<String, Entry>> getOrderingComparator() {
        return comparator;
    }

    Consumer<Object> getLinePrinter() {
        return linePrinter;
    }

    FootprintResultSizeFormat getSizeFormat() {
        return sizeFormat;
    }

    int getLimit() {
        return limit;
    }

    Map<String, Entry> process(ObjectMemoryMeasurer measurer) {
        val resultByType = new TreeMap<String, Entry>();
        val stack = new ArrayDeque<Variable>();
        val graphRoot = measurer.getGraphRoot();
        stack.push(graphRoot);

        while (!stack.isEmpty()) {
            val variable = stack.pop();
            val typeString = variable.getTypeString();

            resultByType.compute(typeString, (k, v) -> {
                if (v == null) {
                    v = new Entry(0, 0);
                }
                v.incrementCount();

                if (!(variable instanceof HasNestedVariables)) {
                    v.addSize(variable.getSizeInBytes());
                } else if (isNecessaryToAbort(typeString) && variable != graphRoot) {
                    v.addSize(variable.getSizeInBytes());
                } else {
                    val variableWithNested = (HasNestedVariables) variable;
                    v.addSize(variable.getSizeInBytes() - variableWithNested.getNestedVariablesSizeInBytes());
                    variableWithNested.getNestedVariables().forEach(stack::push);
                }
                return v;
            });
        }

        return resultByType;
    }

    private boolean isNecessaryToAbort(String typeString) {
        return stopRegExpressions.stream().anyMatch(regex -> regex.matcher(typeString).matches());
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
