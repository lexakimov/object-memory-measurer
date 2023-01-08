package com.github.lexakimov.omm.types;

import com.github.lexakimov.omm.MemoryMeasureUtil;

/**
 * @author akimov
 * created at: 08.01.2023 09:06
 */
public class ArrayOfPrimitivesVariable extends Variable {

    public ArrayOfPrimitivesVariable(String name, Object array) {
        super(name, array);
    }

    @Override
    public String getTypeString() {
        return this.object.getClass().getComponentType().getSimpleName() + "[]";
    }

    @Override
    public long getSizeInBytes() {
        return MemoryMeasureUtil.getSizeInBytes(this.object);
    }
}
