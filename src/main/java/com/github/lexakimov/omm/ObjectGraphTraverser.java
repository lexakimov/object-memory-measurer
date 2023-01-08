package com.github.lexakimov.omm;

import com.github.lexakimov.omm.traversing.types.ArrayOfPrimitivesType;
import com.github.lexakimov.omm.traversing.types.ObjectType;
import com.github.lexakimov.omm.traversing.types.PrimitiveType;
import com.github.lexakimov.omm.traversing.types.Type;
import com.github.lexakimov.omm.util.ReflectionUtils;
import lombok.SneakyThrows;
import lombok.var;
import java.lang.reflect.Field;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author akimov
 * created at: 06.01.2023 15:19
 */
public class ObjectGraphTraverser {

    private final Deque<Object> stack = new LinkedList<>();

    private Type graphRoot;

    public void traverseFor(boolean object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(byte object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(char object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(short object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(int object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(float object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(long object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(double object) {
        graphRoot = new PrimitiveType(object);
    }

    public void traverseFor(Object object) {
        stack.push(object);
        makeTraverse();
    }

    @SneakyThrows(IllegalAccessException.class)
    private void makeTraverse() {
        Type lastType = null;

        while (!stack.isEmpty()) {
            var object = stack.pop();
            var objectClass = object.getClass();

            if (!objectClass.isPrimitive()) {
                var objectType = new ObjectType(object);
                lastType = addToGraph(lastType, objectType);
                var fields = ReflectionUtils.getObjectFields(object, true, true);

                for (Field f : fields) {
                    f.getName();
                    stack.push(f.get(object));
                }

            } else if (objectClass.isArray()) {
                var componentType = objectClass.getComponentType();
                if (componentType.isPrimitive()) {
                    var arrayOfPrimitivesType = new ArrayOfPrimitivesType(object);
                    lastType = addToGraph(lastType, arrayOfPrimitivesType);
                }
            }
        }
    }

    private Type addToGraph(Type lastType, Type type) {
        if (graphRoot == null) {
            graphRoot = type;
        }
        if (lastType == null) {
            lastType = type;
        }
        return lastType;
    }

    public Type getGraphRoot() {
        if (graphRoot != null) {
            return graphRoot;
        }
        throw new IllegalStateException("graph traversal has not yet been completed");
    }
}
