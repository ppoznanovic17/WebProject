package project.utils;

public class Util {

    public static boolean isEmpty(String s){
        return s == null || s.isEmpty();
    }
    public static String toEmpty(String s) {
        if(s == null || s.equals("")) return "";
        return s;
    }
}
