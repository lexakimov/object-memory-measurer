package com.github.lexakimov.omm.types;

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

    public abstract String getTypeString();

    public abstract long getSizeInBytes();
}
