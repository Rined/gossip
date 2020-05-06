package com.rined.gossip;

public class Utils {
    public static String normalize(String xpath) {
        return String.format("translate(normalize-space(%s), ' ', '')", xpath);
    }
}
