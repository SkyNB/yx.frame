package com.lnet.frame.util;


import java.util.Collection;
import java.util.List;

public class StringHelper {

    public static boolean containsIgnoreCase(final String source, Collection<String> target) {
        return target.stream().anyMatch(source::equalsIgnoreCase);
    }

    public static boolean containsAnyIgnoreCase(List<String> source, Collection<String> target) {
        return source.stream().anyMatch(s -> containsIgnoreCase(s, target));
    }

}
