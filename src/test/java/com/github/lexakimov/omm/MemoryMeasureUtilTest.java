package com.github.lexakimov.omm;

import lombok.var;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemoryMeasureUtilTest {

    @Nested
    class GetSizeInBytes {

        @Test
        void primitiveTypeBoolean() {
            boolean b = true;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(b);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeChar() {
            boolean c = true;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(c);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeByte() {
            byte b = 100;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(b);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeShort() {
            short b = 100;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(b);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeInt() {
            int n = 100;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(n);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeFloat() {
            float f = 100.0f;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(f);
            assertThat(sizeInBytes).isEqualTo(16);
        }

        @Test
        void primitiveTypeLong() {
            long l = 100L;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(l);
            assertThat(sizeInBytes).isEqualTo(24);
        }

        @Test
        void primitiveTypeDouble() {
            double d = 100.0d;
            var sizeInBytes = MemoryMeasureUtil.getSizeInBytes(d);
            assertThat(sizeInBytes).isEqualTo(24);
        }
    }

    @Test
    void nullType() {
        assertThatThrownBy(() -> MemoryMeasureUtil.getSizeInBytes(null))
                .isInstanceOf(NullPointerException.class);
    }

}