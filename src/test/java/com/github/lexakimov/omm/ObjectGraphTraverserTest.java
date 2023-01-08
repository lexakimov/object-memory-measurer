package com.github.lexakimov.omm;

import com.github.lexakimov.omm.classes.ClassWithOneIntField;
import com.github.lexakimov.omm.classes.ClassWithTwoIntField;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLong;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLongAndOneNullObject;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLongAndTwoNullObject;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneNonNullObject;
import com.github.lexakimov.omm.classes.EmptyClass;
import com.github.lexakimov.omm.traversing.types.ArrayOfPrimitivesType;
import com.github.lexakimov.omm.traversing.types.ObjectType;
import com.github.lexakimov.omm.traversing.types.PrimitiveType;
import lombok.var;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings("Convert2MethodRef")
class ObjectGraphTraverserTest {

    public static final String CLASS_NAME_PREFIX = "com.github.lexakimov.omm.ObjectGraphTraverserTest";

    @BeforeEach
    void setUp() {
        Assertions.setExtractBareNamePropertyMethods(true);
    }

    @Test
    void notYetTraversed() {
        var uut = new ObjectGraphTraverser();
        assertThatThrownBy(() -> uut.getGraphRoot())
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("graph traversal has not yet been completed");
    }

    @Nested
    class Primitives {

        @Test
        void traverseBoolean() {
            var uut = new ObjectGraphTraverser();
            boolean i = true;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "boolean")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseByte() {
            var uut = new ObjectGraphTraverser();
            byte i = 1;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "byte")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseChar() {
            var uut = new ObjectGraphTraverser();
            char i = 1;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "char")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseShort() {
            var uut = new ObjectGraphTraverser();
            short i = 1;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "short")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseInt() {
            var uut = new ObjectGraphTraverser();
            int i = 100;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "int")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseFloat() {
            var uut = new ObjectGraphTraverser();
            float i = 1;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "float")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseLong() {
            var uut = new ObjectGraphTraverser();
            long i = 1;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "long")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);
        }

        @Test
        void traverseDouble() {
            var uut = new ObjectGraphTraverser();
            double i = 1;
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveType.class)
                    .hasFieldOrPropertyWithValue("typeString", "double")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);
        }
    }

    @Nested
    class ArraysOfPrimitives {

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 32", "100, 120"})
        void traverseBooleanArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            boolean[] i = new boolean[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "boolean[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 32", "100, 120"})
        void traverseByteArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            byte[] i = new byte[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "byte[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 40", "100, 216"})
        void traverseCharArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            char[] i = new char[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "char[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 40", "100, 216"})
        void traverseShortArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            short[] i = new short[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "short[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 32", "10, 56", "100, 416"})
        void traverseIntArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            int[] i = new int[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "int[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 32", "10, 56", "100, 416"})
        void traverseFloatArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            float[] i = new float[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "float[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 32", "3, 40", "10, 96", "100, 816"})
        void traverseLongArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            long[] i = new long[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "long[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 32", "3, 40", "10, 96", "100, 816"})
        void traverseDoubleArray(int size, long sizeInBytes) {
            var uut = new ObjectGraphTraverser();
            double[] i = new double[size];
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesType.class)
                    .hasFieldOrPropertyWithValue("typeString", "double[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

    }

    @Nested
    class Objects {

        @Test
        void traverseObjectType_1() {
            var uut = new ObjectGraphTraverser();
            var i = new EmptyClass();
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", EmptyClass.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L)
            ;
        }

        @Test
        void traverseObjectType_2() {
            var uut = new ObjectGraphTraverser();
            var i = new ClassWithOneIntField();
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithOneIntField.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseObjectType_3() {
            var uut = new ObjectGraphTraverser();
            var i = new ClassWithTwoIntField();
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntField.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);
        }

        @Test
        void traverseObjectType_4() {
            var uut = new ObjectGraphTraverser();
            var i = new ClassWithTwoIntFieldAndOneLong();
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneLong.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 32L);
        }

        @Test
        void traverseObjectType_5() {
            var uut = new ObjectGraphTraverser();
            var i = new ClassWithTwoIntFieldAndOneLongAndOneNullObject();
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneLongAndOneNullObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 32L);
        }

        @Test
        void traverseObjectType_6() {
            var uut = new ObjectGraphTraverser();
            var i = new ClassWithTwoIntFieldAndOneLongAndTwoNullObject();
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneLongAndTwoNullObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 40L)
                    .matches(t -> ((ObjectType) t).getNestedNonNullFields().isEmpty());
        }

        @Test
        void traverseObjectType_7() {
            var uut = new ObjectGraphTraverser();
            var i = new ClassWithTwoIntFieldAndOneNonNullObject();
            uut.traverseFor(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneNonNullObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);

            ObjectType objectType = (ObjectType) graphRoot;

            var nestedNonNullFields = objectType.getNestedNonNullFields();
            assertThat(nestedNonNullFields).hasSize(1).singleElement()
                    .isExactlyInstanceOf(ObjectType.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneNonNullObject.SomeObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);

        }


    }


}