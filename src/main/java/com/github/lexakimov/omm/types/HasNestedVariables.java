package com.github.lexakimov.omm.types;

import java.util.List;

/**
 * @author akimov
 * created at: 08.01.2023 20:20
 */
public interface HasNestedVariables {

    /**
     * @return list of non-null variables.
     */
    List<Variable> getNestedVariables();

    long getNestedVariablesSizeInBytes();

}
