package com.github.lexakimov.omm.traversing.types;

import com.github.lexakimov.omm.MemoryMeasureUtil;
import com.github.lexakimov.omm.util.ReflectionUtils;
import com.github.lexakimov.omm.util.TriFunction;
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
public class ObjectVariable extends Variable {

    private final List<Variable> nestedNonNullFields = new LinkedList<>();

    public ObjectVariable(String name, Object object) {
        super(name, object);
    }

    public List<Variable> getNestedNonNullFields() {
        return nestedNonNullFields;
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
                nestedNonNullFields.add(variable);
                stack.push(variable);
            }
        }
    }

    @Override
    public String getTypeString() {
        return object.getClass().getName();
    }

    @Override
    public long getSizeInBytes() {
        return MemoryMeasureUtil.getSizeInBytes(this.object);
    }
}
