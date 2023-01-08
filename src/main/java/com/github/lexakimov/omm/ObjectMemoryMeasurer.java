package com.github.lexakimov.omm;

import com.github.lexakimov.omm.types.ArrayOfObjects;
import com.github.lexakimov.omm.types.ArrayOfPrimitivesVariable;
import com.github.lexakimov.omm.types.HasNestedVariables;
import com.github.lexakimov.omm.types.ObjectVariable;
import com.github.lexakimov.omm.types.PrimitiveVariable;
import com.github.lexakimov.omm.types.Variable;
import lombok.var;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author akimov
 * created at: 06.01.2023 15:19
 */
public class ObjectMemoryMeasurer {

    private final Deque<Variable> stack = new LinkedList<>();

    private Variable graphRoot;

    public void traverse(boolean object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(byte object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(char object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(short object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(int object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(float object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(long object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(double object) {
        checkNoInteraction();
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverse(Object object) {
        checkNoInteraction();
        graphRoot = variableFactory("Root", object, false);
        stack.push(graphRoot);
        makeTraverse();
    }

    private void checkNoInteraction() {
        if (graphRoot != null) {
            throw new IllegalStateException("traverse method cannot be called more than once");
        }
    }

    private void makeTraverse() {
        while (!stack.isEmpty()) {
            var variable = stack.pop();
            variable.process(stack, this::variableFactory);
        }
    }

    private Variable variableFactory(String name, Object value, boolean isPrimitive) {
        var objectClass = value.getClass();

        if (objectClass.isArray()) {
            var componentType = objectClass.getComponentType();
            if (componentType.isPrimitive()) {
                return new ArrayOfPrimitivesVariable(name, value);
            } else {
                return new ArrayOfObjects(name, value);
            }
        } else if (!objectClass.isPrimitive() && !isPrimitive) {
            return new ObjectVariable(name, value);
        }
        return null;
    }

    public Variable getGraphRoot() {
        if (graphRoot != null) {
            return graphRoot;
        }
        throw new IllegalStateException("graph traversal has not yet been completed");
    }
}
