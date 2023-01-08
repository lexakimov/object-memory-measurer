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
        type = boolean.class.getTypeName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, byte i) {
        super(name, i);
        type = byte.class.getTypeName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, char i) {
        super(name, i);
        type = char.class.getTypeName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, short i) {
        super(name, i);
        type = short.class.getTypeName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, int i) {
        super(name, i);
        type = int.class.getTypeName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, float i) {
        super(name, i);
        type = float.class.getTypeName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, long i) {
        super(name, i);
        type = long.class.getTypeName();
        sizeInBytes = MemoryMeasureUtil.getSizeInBytes(i);
    }

    public PrimitiveVariable(String name, double i) {
        super(name, i);
        type = double.class.getTypeName();
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
