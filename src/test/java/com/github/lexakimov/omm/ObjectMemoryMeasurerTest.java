package com.github.lexakimov.omm;

import com.github.lexakimov.omm.classes.AbstractClass;
import com.github.lexakimov.omm.classes.ClassWithOneIntField;
import com.github.lexakimov.omm.classes.ClassWithTwoIntField;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLong;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLongAndOneNullObject;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneLongAndTwoNullObject;
import com.github.lexakimov.omm.classes.ClassWithTwoIntFieldAndOneNonNullObject;
import com.github.lexakimov.omm.classes.ClassWithTwoNonNullObjects;
import com.github.lexakimov.omm.classes.ClassWithoutFields;
import com.github.lexakimov.omm.classes.NonAbstractClass1;
import com.github.lexakimov.omm.classes.NonAbstractClass2;
import com.github.lexakimov.omm.types.ArrayOfObjects;
import com.github.lexakimov.omm.types.ArrayOfPrimitivesVariable;
import com.github.lexakimov.omm.types.ObjectVariable;
import com.github.lexakimov.omm.types.PrimitiveVariable;
import com.github.lexakimov.omm.types.Variable;
import lombok.var;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings("Convert2MethodRef")
class ObjectMemoryMeasurerTest {

    @BeforeEach
    void setUp() {
        Assertions.setExtractBareNamePropertyMethods(true);
    }

    @Test
    void notYetTraversed() {
        var uut = new ObjectMemoryMeasurer();
        assertThatThrownBy(() -> uut.getGraphRoot())
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("graph traversal has not yet been completed");
    }

    @Test
    void alreadyTraversed() {
        var uut = new ObjectMemoryMeasurer();
        uut.traverse(1);

        assertThatThrownBy(() -> uut.traverse(1))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("traverse method cannot be called more than once");
    }

    @Nested
    class Primitives {

        @Test
        void traverseBoolean() {
            boolean i = true;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "boolean")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseByte() {
            byte i = 1;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "byte")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseChar() {
            char i = 1;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "char")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseShort() {
            short i = 1;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "short")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseInt() {
            int i = 100;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "int")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseFloat() {
            float i = 1;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "float")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseLong() {
            long i = 1;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "long")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);
        }

        @Test
        void traverseDouble() {
            double i = 1;
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(PrimitiveVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "double")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);
        }
    }

    @Nested
    class ArraysOfPrimitives {

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 32", "100, 120"})
        void traverseBooleanArray(int size, long sizeInBytes) {
            boolean[] i = new boolean[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "boolean[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 32", "100, 120"})
        void traverseByteArray(int size, long sizeInBytes) {
            byte[] i = new byte[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "byte[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 40", "100, 216"})
        void traverseCharArray(int size, long sizeInBytes) {
            char[] i = new char[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "char[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 24", "10, 40", "100, 216"})
        void traverseShortArray(int size, long sizeInBytes) {
            short[] i = new short[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "short[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 32", "10, 56", "100, 416"})
        void traverseIntArray(int size, long sizeInBytes) {
            int[] i = new int[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "int[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 24", "3, 32", "10, 56", "100, 416"})
        void traverseFloatArray(int size, long sizeInBytes) {
            float[] i = new float[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "float[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 32", "3, 40", "10, 96", "100, 816"})
        void traverseLongArray(int size, long sizeInBytes) {
            long[] i = new long[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "long[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

        @ParameterizedTest
        @CsvSource({"0, 16", "1, 24", "2, 32", "3, 40", "10, 96", "100, 816"})
        void traverseDoubleArray(int size, long sizeInBytes) {
            double[] i = new double[size];
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "double[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", sizeInBytes);
        }

    }

    @Nested
    class Objects {

        @Test
        void traverseObjectType_anonymous() {
            var i = new TreeMap<String, String>(){};
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "java.util.TreeMap");
        }

        @SuppressWarnings("InstantiationOfUtilityClass")
        @Test
        void traverseObjectType_1() {
            var i = new ClassWithoutFields();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithoutFields.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 0L);
        }

        @Test
        void traverseObjectType_2() {
            var i = new ClassWithOneIntField();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithOneIntField.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 0L);
        }

        @Test
        void traverseObjectType_3() {
            var i = new ClassWithTwoIntField();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntField.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 0L);
        }

        @Test
        void traverseObjectType_4() {
            var i = new ClassWithTwoIntFieldAndOneLong();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneLong.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 32L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 0L);
        }

        @Test
        void traverseObjectType_5() {
            var i = new ClassWithTwoIntFieldAndOneLongAndOneNullObject();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneLongAndOneNullObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 32L);
        }

        @Test
        void traverseObjectType_6() {
            var i = new ClassWithTwoIntFieldAndOneLongAndTwoNullObject();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneLongAndTwoNullObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 40L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 0L)
                    .matches(t -> ((ObjectVariable) t).getNestedVariables().isEmpty());
        }

        @Test
        void traverseObjectType_7() {
            var i = new ClassWithTwoIntFieldAndOneNonNullObject();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneNonNullObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 40L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 16L);

            ObjectVariable objectType = (ObjectVariable) graphRoot;

            var nestedVariables = objectType.getNestedVariables();
            assertThat(nestedVariables).hasSize(1).singleElement()
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoIntFieldAndOneNonNullObject.SomeObject.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 0L);
        }

        @Test
        void traverseObjectType_8() {
            var i = new ClassWithTwoNonNullObjects();
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("name", "Root")
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoNonNullObjects.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 20672L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 20648L);

            var nestedVariables1 = ((ObjectVariable) graphRoot).getNestedVariables();
            var obj1 = nestedVariables1.get(0);
            assertThat(obj1)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("name", "obj1")
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoNonNullObjects.NonNullObject1.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 4128L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 4112L);

            var nestedVariables2 = ((ObjectVariable) obj1).getNestedVariables();
            var obj1_1 = nestedVariables2.get(0);
            assertThat(obj1_1)
                    .isExactlyInstanceOf(ArrayOfPrimitivesVariable.class)
                    .hasFieldOrPropertyWithValue("name", "obj1_1")
                    .hasFieldOrPropertyWithValue("typeString", int[].class.getTypeName())
                    .hasFieldOrPropertyWithValue("sizeInBytes", 4112L);

            var obj2 = nestedVariables1.get(1);
            assertThat(obj2)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("name", "obj2")
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoNonNullObjects.NonNullObject2.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16520L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 16496L);

            var nestedVariables3 = ((ObjectVariable) obj2).getNestedVariables();
            var obj2_1 = nestedVariables3.get(0);
            assertThat(obj2_1)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("name", "obj2_1")
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoNonNullObjects.NonNullObject3.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 8248L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 8224L);

            var obj2_2 = nestedVariables3.get(1);
            assertThat(obj2_2)
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("name", "obj2_2")
                    .hasFieldOrPropertyWithValue("typeString", ClassWithTwoNonNullObjects.NonNullObject3.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 8248L)
                    .hasFieldOrPropertyWithValue("nestedVariablesSizeInBytes", 8224L);
        }
    }

    @Nested
    class ArraysOfObjects {

        @Test
        void traverseObjectArray_1() {
            var i = new Object[]{};
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfObjects.class)
                    .hasFieldOrPropertyWithValue("typeString", "java.lang.Object[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 16L);
        }

        @Test
        void traverseObjectArray_2() {
            var i = new Object[]{null};
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfObjects.class)
                    .hasFieldOrPropertyWithValue("typeString", "java.lang.Object[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);
        }

        @Test
        void traverseObjectArray_3() {
            var i = new Object[]{null, null};
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfObjects.class)
                    .hasFieldOrPropertyWithValue("typeString", "java.lang.Object[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 24L);
        }

        @Test
        void traverseObjectArray_4() {
            var i = new Object[]{null, null, null};
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            var graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfObjects.class)
                    .hasFieldOrPropertyWithValue("typeString", "java.lang.Object[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 32L);
        }

        @Test
        void traverseObjectArray_5() {
            var i = new AbstractClass[]{
                    null,
                    null,
                    new NonAbstractClass1(),
                    new NonAbstractClass2(),
                    new AbstractClass() {
                    },
                    null,
                    null
            };
            var uut = new ObjectMemoryMeasurer();
            uut.traverse(i);
            Variable graphRoot = assertDoesNotThrow(() -> uut.getGraphRoot());
            assertThat(graphRoot)
                    .isExactlyInstanceOf(ArrayOfObjects.class)
                    .hasFieldOrPropertyWithValue("typeString", AbstractClass.FQN + "[]")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 448L);

            ArrayOfObjects objectType = (ArrayOfObjects) graphRoot;

            var nestedVariables = objectType.getNestedVariables();
            assertThat(nestedVariables).hasSize(3);

            assertThat(nestedVariables.get(0))
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", NonAbstractClass1.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 120L);

            assertThat(nestedVariables.get(1))
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", NonAbstractClass2.FQN)
                    .hasFieldOrPropertyWithValue("sizeInBytes", 168L);

            assertThat(nestedVariables.get(2))
                    .isExactlyInstanceOf(ObjectVariable.class)
                    .hasFieldOrPropertyWithValue("typeString", "com.github.lexakimov.omm.ObjectMemoryMeasurerTest$ArraysOfObjects$1")
                    .hasFieldOrPropertyWithValue("sizeInBytes", 112L);
        }
    }

}