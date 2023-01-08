package com.github.lexakimov.omm.types;

import com.github.lexakimov.omm.util.TriFunction;
import lombok.SneakyThrows;
import lombok.val;
import java.lang.reflect.Array;
import java.util.Deque;

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
    public void process(Deque<Variable> stack, TriFunction<String, Object, Boolean, Variable> factoryMethod) {
        int arrayLength = Array.getLength(object);
        for (int i = 0; i < arrayLength; i++) {
            Object arrayElement = Array.get(object, i);
            if (arrayElement == null) {
                continue;
            }

            val variable = factoryMethod.apply("[" + i + "]", arrayElement, false);
            if (variable != null) {
                nestedVariables.add(variable);
                stack.push(variable);
            }
        }
    }
}
