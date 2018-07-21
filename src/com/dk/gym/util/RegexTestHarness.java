package com.dk.gym.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegexTestHarness {

    public static void main(String[] args) {

        //"^.{0,10}$"
        //"^[-]"
        //"[A-Z]"
        //"\\d"
        //"\\d"
        //^\d*$
        //"^.+@.+\\.\\w{2,6}$"
        //"^\\d{2}[:]\\d{2}$"
//"^\\d{4}[:]\\d\\d$"


    String regex = "\\d{2}(?=[:])";

    String testString = "20:00";

    Pattern pattern = Pattern.compile(regex);  //deleted Pattern.CASE-INSENSITIVE

    Matcher matcher = pattern.matcher(testString);

        boolean found = false;
        while (matcher.find()) {
            System.out.printf("I found the text" +
                            " \"%s\" starting at " +
                            "index %d and ending at index %d.%n",
                    matcher.group(),
                    matcher.start(),
                    matcher.end());
            found = true;
        }
        if(!found){
            System.out.printf("No match found.%n");
        }

        System.out.println(Arrays.toString(testString.split(regex)));

    }

}
