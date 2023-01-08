package com.github.lexakimov.omm.traversing.types;

import com.github.lexakimov.omm.ObjectMemoryMeasurer;
import java.util.LinkedList;
import java.util.List;

/**
 * @author akimov
 * created at: 08.01.2023 09:46
 */
public class ObjectType extends Type {

    private final List<ObjectType> nestedNonNullFields = new LinkedList<>();
    private String name;

    public ObjectType(Object object) {
        super(object.getClass().getName(), ObjectMemoryMeasurer.getSizeInBytes(object));
    }

    public void addNonNullField(ObjectType field) {
        nestedNonNullFields.add(field);
    }

    public List<ObjectType> getNestedNonNullFields() {
        return nestedNonNullFields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
