package com.github.lexakimov.omm.traversing.types;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;

/**
 * @author akimov
 * created at: 08.01.2023 09:06
 */
public class ArrayOfPrimitivesType extends Type {

    public ArrayOfPrimitivesType(Object array) {
        super(array.getClass().getComponentType().getSimpleName() + "[]", ObjectMemoryMeasurer.getSizeInBytes(array));
    }

}
