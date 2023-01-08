package com.github.lexakimov.omm.traversing.types;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;

/**
 * @author akimov
 * created at: 08.01.2023 08:22
 */
public final class PrimitiveType extends Type {

    public PrimitiveType(boolean i) {
        super(boolean.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

    public PrimitiveType(byte i) {
        super(byte.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

    public PrimitiveType(char i) {
        super(char.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

    public PrimitiveType(short i) {
        super(short.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

    public PrimitiveType(int i) {
        super(int.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

    public PrimitiveType(float i) {
        super(float.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

    public PrimitiveType(long i) {
        super(long.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

    public PrimitiveType(double i) {
        super(double.class.getName(), ObjectMemoryMeasurer.getSizeInBytes(i));
    }

}
