package com.github.lexakimov.omm.types;

import com.github.lexakimov.omm.MemoryMeasureUtil;
import com.github.lexakimov.omm.util.TriFunction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.util.Deque;

/**
 * @author akimov
 * created at: 08.01.2023 08:23
 */
@Getter
@EqualsAndHashCode
public abstract class Variable {
    protected final Object object;
    protected final String name;

    protected Variable(String name, Object object) {
        this.name = name;
        this.object = object;
    }

    public String getName() {
        return this.name;
    }

    public void process(Deque<Variable> stack, TriFunction<String, Object, Boolean, Variable> factoryMethod) {

    }

    public String getTypeString() {
        return object.getClass().getTypeName();
    }

    /**
     * @return total size of object (with nested)
     */
    public long getSizeInBytes() {
        return MemoryMeasureUtil.getSizeInBytes(object);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{ " +
               "name:'" + name + "', " +
               "type: " + getTypeString() + ", " +
               "size: " + getSizeInBytes() +
               " }";
    }
}
