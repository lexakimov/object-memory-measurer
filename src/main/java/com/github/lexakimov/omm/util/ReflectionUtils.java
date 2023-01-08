package com.github.lexakimov.omm.util;

import lombok.SneakyThrows;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author akimov
 * created at: 06.01.2023 14:44
 */
public class ReflectionUtils {

    public static List<Field> getObjectFields(Object object) {
        List<Field> fields = new ArrayList<>(8);
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        // all together so there is only one security check
        AccessibleObject.setAccessible(fields.toArray(new AccessibleObject[0]), true);
        return fields;
    }

    @SneakyThrows
    public static List<Field> getObjectFields(Object object, boolean excludeStatic, boolean excludeNulls) {
        LinkedList<Field> result = new LinkedList<>();
        for (Field field : getObjectFields(object)) {
            if (excludeStatic && Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (excludeNulls && field.get(object) == null) {
                continue;
            }

            result.add(field);
        }
        return result;
    }


}
