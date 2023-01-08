package com.github.lexakimov.omm.types;

import com.github.lexakimov.omm.util.ReflectionUtils;
import com.github.lexakimov.omm.util.TriFunction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import java.lang.reflect.Field;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author akimov
 * created at: 08.01.2023 09:46
 */
@EqualsAndHashCode(callSuper = true)
public class ObjectVariable extends Variable implements HasNestedVariables {

    @Getter
    protected final List<Variable> nestedVariables = new LinkedList<>();

    private long nestedVariablesSize = -1;

    public ObjectVariable(String name, Object object) {
        super(name, object);
    }

    @SneakyThrows
    @Override
    public void process(Deque<Variable> stack, TriFunction<String, Object, Boolean, Variable> factoryMethod) {
        var fields = ReflectionUtils.getObjectFields(object, true, true);
        for (Field field : fields) {
            val fieldValue = field.get(object);
            val isPrimitive = field.getType().isPrimitive();
            val variable = factoryMethod.apply(field.getName(), fieldValue, isPrimitive);
            if (variable != null) {
                nestedVariables.add(variable);
                stack.push(variable);
            }
        }
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

}
