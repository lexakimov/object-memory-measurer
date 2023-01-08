package com.github.lexakimov.omm;

import com.github.lexakimov.omm.traversing.types.ArrayOfPrimitivesVariable;
import com.github.lexakimov.omm.traversing.types.ObjectVariable;
import com.github.lexakimov.omm.traversing.types.PrimitiveVariable;
import com.github.lexakimov.omm.traversing.types.Variable;
import lombok.var;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author akimov
 * created at: 06.01.2023 15:19
 */
public class ObjectMemoryMeasurer {

    private final Deque<Variable> stack = new LinkedList<>();

    private Variable graphRoot;

    public void traverseFor(boolean object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(byte object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(char object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(short object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(int object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(float object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(long object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(double object) {
        graphRoot = new PrimitiveVariable("Root", object);
    }

    public void traverseFor(Object object) {
        graphRoot = variableFactory("Root", object, false);
        stack.push(graphRoot);
        makeTraverse();
    }

    private void makeTraverse() {
        while (!stack.isEmpty()) {
            var variable = stack.pop();
            variable.process(stack, this::variableFactory);
        }
    }

    private Variable variableFactory(String name, Object object, boolean isPrimitive) {
        var objectClass = object.getClass();

        if (objectClass.isArray()) {
            var componentType = objectClass.getComponentType();
            if (componentType.isPrimitive()) {
                return new ArrayOfPrimitivesVariable(name, object);
            }
        } else if (!objectClass.isPrimitive() && !isPrimitive) {
            return new ObjectVariable(name, object);
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
