package com.github.lexakimov.omm.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * <pre>
 *                              SI     BINARY
 *                   0:        0 B        0 B
 *                  27:       27 B       27 B
 *                 999:      999 B      999 B
 *                1000:     1.0 kB     1000 B
 *                1023:     1.0 kB     1023 B
 *                1024:     1.0 kB    1.0 KiB
 *                1728:     1.7 kB    1.7 KiB
 *              110592:   110.6 kB  108.0 KiB
 *             7077888:     7.1 MB    6.8 MiB
 *           452984832:   453.0 MB  432.0 MiB
 *         28991029248:    29.0 GB   27.0 GiB
 *       1855425871872:     1.9 TB    1.7 TiB
 * 9223372036854775807:     9.2 EB    8.0 EiB   (Long.MAX_VALUE)
 * </pre>
 *
 * @author akimov
 * created at: 08.01.2023 08:13
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * SI (1 k = 1,000)
     */
    public static String byteCountAsSiString(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

    /**
     * Binary (1 Ki = 1,024)
     */
    public static String byteCountAsBinString(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }

}
