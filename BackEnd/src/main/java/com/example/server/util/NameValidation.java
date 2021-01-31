package com.example.server.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidation {
    public static Boolean containNumber(String name){
        Pattern p = Pattern.compile("[0-9]");
        Matcher mLetter = p.matcher(name);
        return mLetter.find();
    }

    public static Boolean containSpecialCharacter(String address){
        Pattern p = Pattern.compile("[$&+,:;=\\\\?@#|'<>.^*()%!-]");
        Matcher mLetter = p.matcher(address);
        return mLetter.find();
    }
}
