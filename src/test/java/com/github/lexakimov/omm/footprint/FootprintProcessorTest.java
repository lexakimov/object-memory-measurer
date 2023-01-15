package com.github.lexakimov.omm.footprint;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;
import com.github.lexakimov.omm.types.ArrayOfPrimitivesVariable;
import com.github.lexakimov.omm.types.ObjectVariable;
import com.github.lexakimov.omm.types.PrimitiveVariable;
import com.github.lexakimov.omm.types.Variable;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("UnnecessaryBoxing")
class FootprintProcessorTest {

    @Test
    void withIntRoot() {
        val root = new PrimitiveVariable("Root", 123);
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();
        val result = uut.process(measurer);
        assertThat(result)
                .hasSize(1)
                .extractingByKey("int")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 16L);
    }

    @Test
    void withLongRoot() {
        val root = new PrimitiveVariable("Root", 123L);
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();
        val result = uut.process(measurer);
        assertThat(result)
                .hasSize(1)
                .extractingByKey("long")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
    }

    @Test
    void withArrayOfIntRoot() {
        val root = new ArrayOfPrimitivesVariable("Root", new int[10]);
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();
        val result = uut.process(measurer);
        assertThat(result)
                .hasSize(1)
                .extractingByKey("int[]")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 56L);
    }

    @Test
    void withObjectRoot() {
        val root = new ObjectVariable("Root", "String 1");
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();
        val result = uut.process(measurer);
        assertThat(result)
                .hasSize(1)
                .extractingByKey("java.lang.String")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
    }

    @Test
    void objectRootWithNestedObject() {
        val root = new ObjectVariable("Root", "String 1");
        val nested1 = new ObjectVariable("lev 1 obj 1", "String 2");
        nested1.getNestedVariablesSizeInBytes();
        root.getNestedVariables().add(nested1);
        root.getNestedVariablesSizeInBytes();

        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();
        val result = uut.process(measurer);
        assertThat(result)
                .hasSize(1)
                .extractingByKey("java.lang.String")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 2L)
                .hasFieldOrPropertyWithValue("size", 48L);
    }

    @Test
    void objectRootWithNestedObjects() {
        val root = prepareGraph();
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();

        val result = uut.process(measurer);
        val mapAssert = assertThat(result).hasSize(4);
        mapAssert
                .extractingByKey("java.lang.String")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.lang.Long")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 2L)
                .hasFieldOrPropertyWithValue("size", 48L);
        mapAssert
                .extractingByKey("java.lang.Double")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 2L)
                .hasFieldOrPropertyWithValue("size", 48L);
        mapAssert
                .extractingByKey("java.util.HashMap")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 48L);
    }

    @Test
    void objectRootWithNestedObjectsAndStopWord_1() {
        val root = prepareGraph();
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();

        uut.registerStopWord("java.util.HashMap");

        val result = uut.process(measurer);
        val mapAssert = assertThat(result).hasSize(4);
        mapAssert
                .extractingByKey("java.lang.String")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.lang.Long")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 2L)
                .hasFieldOrPropertyWithValue("size", 48L);
        mapAssert
                .extractingByKey("java.lang.Double")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.util.HashMap")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 72L);
    }

    @Test
    void objectRootWithNestedObjectsAndStopWord_2() {
        val root = prepareGraph();
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();

        uut.registerStopWord("java.lang.Double");

        val result = uut.process(measurer);
        val mapAssert = assertThat(result).hasSize(3);
        mapAssert
                .extractingByKey("java.lang.String")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.lang.Long")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.lang.Double")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 120L);
    }

    @Test
    void objectRootWithNestedObjectsAndStopWordRegexp_1() {
        val root = prepareGraph();
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();

        uut.registerStopWordRegexp("java\\.util\\.H.sh.*");

        val result = uut.process(measurer);
        val mapAssert = assertThat(result).hasSize(4);
        mapAssert
                .extractingByKey("java.lang.String")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.lang.Long")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 2L)
                .hasFieldOrPropertyWithValue("size", 48L);
        mapAssert
                .extractingByKey("java.lang.Double")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.util.HashMap")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 72L);
    }

    @Test
    void objectRootWithNestedObjectsAndStopWordRegexp_2() {
        val root = prepareGraph();
        val measurer = mockMeasurer(root);
        val uut = new FootprintProcessor();

        uut.registerStopWordRegexp("java.l.ng.Do.*");

        val result = uut.process(measurer);
        val mapAssert = assertThat(result).hasSize(3);
        mapAssert
                .extractingByKey("java.lang.String")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.lang.Long")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 24L);
        mapAssert
                .extractingByKey("java.lang.Double")
                .isNotNull()
                .hasFieldOrPropertyWithValue("count", 1L)
                .hasFieldOrPropertyWithValue("size", 120L);
    }

    private static ObjectMemoryMeasurer mockMeasurer(Variable root) {
        val measurer = Mockito.mock(ObjectMemoryMeasurer.class);
        when(measurer.getGraphRoot()).thenReturn(root);
        return measurer;
    }

    private static ObjectVariable prepareGraph() {
        // string           24 + 144 = 168
        //    long          24 + 0   = 24
        //    double        24 + 96  = 120
        //       long       24 + 0   = 24
        //       hashMap    48 + 24  = 72
        //          double  24 + 0   = 24
        val root = new ObjectVariable("Root", "String 1");
        val nested1_1 = new ObjectVariable("lev 1 obj 1", Long.valueOf(123));
        nested1_1.getNestedVariablesSizeInBytes();
        val nested1_2 = new ObjectVariable("lev 1 obj 2", Double.valueOf(1));
        val nested2_1 = new ObjectVariable("lev 2 obj 1", Long.valueOf(123));
        nested2_1.getNestedVariablesSizeInBytes();
        val nested2_2 = new ObjectVariable("lev 2 obj 2", new HashMap<>(1));
        val nested3_1 = new ObjectVariable("lev 3 obj 1", Double.valueOf(1));
        nested3_1.getNestedVariablesSizeInBytes();
        nested2_2.getNestedVariables().add(nested3_1);
        nested2_2.getNestedVariablesSizeInBytes();
        nested1_2.getNestedVariables().add(nested2_1);
        nested1_2.getNestedVariables().add(nested2_2);
        nested1_2.getNestedVariablesSizeInBytes();
        root.getNestedVariables().add(nested1_1);
        root.getNestedVariables().add(nested1_2);
        root.getNestedVariablesSizeInBytes();

        return root;
    }

}