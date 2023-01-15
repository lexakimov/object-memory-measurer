package com.github.lexakimov.omm.footprint;

import com.github.lexakimov.omm.util.StringUtils;
import java.util.function.Function;

/**
 * @author akimov
 * created at: 15.01.2023 16:38
 */
public enum FootprintResultSizeFormat {

    RAW(count -> count + " B"),
    BINARY(StringUtils::byteCountAsBinString),
    SI(StringUtils::byteCountAsSiString);

    private final Function<Long, String> formattingFunction;

    FootprintResultSizeFormat(Function<Long, String> formattingFunction) {
        this.formattingFunction = formattingFunction;
    }

    public Function<Long, String> getFormattingFunction() {
        return formattingFunction;
    }
}
