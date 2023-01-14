package com.github.lexakimov.omm.example;

import com.github.lexakimov.omm.util.Logger;
import lombok.var;
import static com.github.lexakimov.omm.MemoryMeasureUtil.getSizeInBytes;
import static com.github.lexakimov.omm.MemoryMeasureUtil.printSizeInBytes;
import static com.github.lexakimov.omm.MemoryMeasureUtil.printWithClassName;

public class MemoryMeasureUtilExample {

    public static void main(String[] args) {
        printWithClassName(true);
        printWithClassName((byte) 1);
        printWithClassName((char) 1);
        printWithClassName((short) 1);
        printWithClassName(1);
        printWithClassName(1.0f);
        printWithClassName(1L);
        printWithClassName(1.0d);

        Logger.info("int");
        for (int i = 0; i <= 1024; i++) {
            Logger.info("i = " + i);
            printSizeInBytes(new int[i]);
            Logger.info(String.valueOf(128 + 32 * (i + i % 2)));
            Logger.info("---------------------------------------------");
        }

        {
            Logger.info("Integer");
            int i = 1024;
            var arr = new Integer[i];
            var obj = Integer.valueOf(1);
            var size = getSizeInBytes(arr) + getSizeInBytes(obj) * i;
            Logger.info(size);
        }

        {
            Logger.info("int");
            int i = 1024;
            var arr = new int[i];
            var size = getSizeInBytes(arr);
            Logger.info(String.valueOf(size));
        }

        Logger.info("long");
        for (int i = 0; i < 1000; i++) {
            Logger.info("i = " + i);
            printSizeInBytes(new long[i]);
            Logger.info(String.valueOf(128 + 64 * i));
            Logger.info("---------------------------------------------");
        }
    }
}