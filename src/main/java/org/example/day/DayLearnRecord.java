package org.example.day;

import org.apache.commons.lang3.StringUtils;

public class DayLearnRecord {
    public static void main(String[] args) {
        String s = "";
        // byte,short,int,long,float,double,boolean,char
        System.out.println(isEmpty(s));
    }

    public static boolean isEmpty(String string) {
        return StringUtils.isEmpty(string);
    }
}
