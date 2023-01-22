package com.github.lexakimov.omm.types;

import com.github.lexakimov.omm.util.TriFunction;
import lombok.SneakyThrows;
import lombok.val;
import java.lang.reflect.Array;
import java.util.Deque;
import java.util.Set;

/**
 * @author akimov
 * created at: 08.01.2023 17:39
 */
public class ArrayOfObjects extends ObjectVariable {

    public ArrayOfObjects(String name, Object object) {
        super(name, object);
    }

    @SneakyThrows
    @Override
    public void process(
            Deque<Variable> stack,
            Set<Long> processed,
            TriFunction<String, Object, Boolean, Variable> factoryMethod
    ) {
        processed.add(identityHashCode(this));
        int arrayLength = Array.getLength(object);
        for (int i = 0; i < arrayLength; i++) {
            Object arrayElement = Array.get(object, i);
            if (arrayElement == null) {
                continue;
            }

            val variable = factoryMethod.apply("[" + i + "]", arrayElement, false);
            if (variable != null) {
                long identityHashCode = identityHashCode(variable);
                if (processed.add(identityHashCode)) {
                    nestedVariables.add(variable);
                    stack.push(variable);
                }
            }
        }
    }
}
