package com.shu.homework.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String args[]) {
//        System.out.println(MD5Util.MD5EncodeUtf8("dewily"));
        List<String> strings = new ArrayList<>();
        strings.add("刘谕鸿");
        strings.add("李林");
        strings.add("张涛");
        strings.removeIf(s -> s.equals("刘谕鸿"));
        System.out.println(strings);
    }
}
