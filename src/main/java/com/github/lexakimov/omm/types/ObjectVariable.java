package com.github.lexakimov.omm.types;

import com.github.lexakimov.omm.util.ReflectionUtils;
import com.github.lexakimov.omm.util.TriFunction;
import com.github.lexakimov.omm.util.UnsafeUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * @author akimov
 * created at: 08.01.2023 09:46
 */
@EqualsAndHashCode(callSuper = true)
public class ObjectVariable extends Variable implements HasNestedVariables {

    @Getter
    protected final List<Variable> nestedVariables = new ArrayList<>();

    private long nestedVariablesSize = -1;

    public ObjectVariable(String name, Object object) {
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
        var fields = ReflectionUtils.getObjectFields(object, true, true);
        for (Field field : fields) {
            val fieldValue = field.get(object);
            val isPrimitive = field.getType().isPrimitive();
            val variable = factoryMethod.apply(field.getName(), fieldValue, isPrimitive);
            if (variable != null && !processed.contains(identityHashCode(variable))) {
                nestedVariables.add(variable);
                stack.push(variable);
            }
        }
    }

    @Override
    public String getTypeString() {
        Class<?> clazz = object.getClass();
        if (clazz.isAnonymousClass()) {
            return (clazz.getInterfaces().length != 0)
                    ? clazz.getInterfaces()[0].getTypeName()
                    : clazz.getSuperclass().getTypeName();
        }

        return super.getTypeString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{ " +
               "name:'" + name + "', " +
               "type: " + getTypeString() + ", " +
               "size: " + getSizeInBytes() + ", " +
               "non-null fields: " + nestedVariables.size() +
               " }";
    }

    @Override
    public long getSizeInBytes() {
        return (nestedVariablesSize > 0)
                ? super.getSizeInBytes() + nestedVariablesSize
                : super.getSizeInBytes();
    }

    @Override
    public long getNestedVariablesSizeInBytes() {
        if (nestedVariablesSize < 0) {
            nestedVariablesSize = nestedVariables.stream().mapToLong(Variable::getSizeInBytes).sum();
        }
        return nestedVariablesSize;
    }

    public static long identityHashCode(Variable variable) {
        return UnsafeUtil.addressOf(variable.object);
    }

}
