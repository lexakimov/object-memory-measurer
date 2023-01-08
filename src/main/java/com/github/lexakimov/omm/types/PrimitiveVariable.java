package com.github.lexakimov.omm.types;

import com.github.lexakimov.omm.MemoryMeasureUtil;

/**
 * @author akimov
 * created at: 08.01.2023 08:22
 */
public final class PrimitiveVariable extends Variable {

    private final String type;
    private final long sizeInBytes;

    public PrimitiveVariable(String name, boolean i) {
        super(name, i);
        type = boolean.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, byte i) {
        super(name, byte.class.getName());
        type = byte.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, char i) {
        super(name, char.class.getName());
        type = char.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, short i) {
        super(name, short.class.getName());
        type = short.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, int i) {
        super(name, int.class.getName());
        type = int.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, float i) {
        super(name, float.class.getName());
        type = float.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, long i) {
        super(name, long.class.getName());
        type = long.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, double i) {
        super(name, double.class.getName());
        type = double.class.getName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    @Override
    public String getTypeString() {
        return type;
    }

    @Override
    public long getSizeInBytes() {
        return sizeInBytes;
    }
}
