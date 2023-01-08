package com.github.lexakimov.omm.traversing.types;

import lombok.EqualsAndHashCode;

/**
 * @author akimov
 * created at: 08.01.2023 08:23
 */
@EqualsAndHashCode
public abstract class Type {

    protected final String typeString;
    protected final long sizeInBytes;

    protected Type(String typeString, long sizeInBytes) {
        this.typeString = typeString;
        this.sizeInBytes = sizeInBytes;
    }

    public String typeString() {
        return typeString;
    }

    public long sizeInBytes() {
        return sizeInBytes;
    }

}
