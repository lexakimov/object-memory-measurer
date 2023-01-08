package com.github.lexakimov.omm;

import lombok.var;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ObjectMemoryMeasurerTest {

    @Nested
    class GetSizeInBytes {

        @Test
        void primitiveTypeBoolean() {
            boolean b = true;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(b);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeChar() {
            boolean c = true;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(c);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeByte() {
            byte b = 100;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(b);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeShort() {
            short b = 100;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(b);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeInt() {
            int n = 100;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(n);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeFloat() {
            float f = 100.0f;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(f);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeLong() {
            long l = 100L;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(l);
            assertThat(sizeInBytes).isEqualTo(24);
        }

        @Test
        void primitiveTypeDouble() {
            double d = 100.0d;
            var sizeInBytes = ObjectMemoryMeasurer.getSizeInBytes(d);
            assertThat(sizeInBytes).isEqualTo(24);
        }
    }

    @Test
    void nullType() {
        assertThatThrownBy(() -> ObjectMemoryMeasurer.getSizeInBytes(null))
                .isInstanceOf(NullPointerException.class);
    }

}