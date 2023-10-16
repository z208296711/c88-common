package com.c88.common.web.util;

import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static <T> T getRandomElement(List<T> tList) {
        Random rand = new Random();
        return tList.get(rand.nextInt(tList.size()));
    }
}
