package com.triple.clubMileage.api;

/**
 * 임의 문자열 생성하는 클래스
 *
 * @author taxol
 */
public final class RandomStringGenerator {

    private RandomStringGenerator() {}

    public static String getRandomString(int i)
    {
        String theAlphaNumericS;
        StringBuilder builder;

        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+ "0123456789";

        //create the StringBuffer
        builder = new StringBuilder(i);

        for (int m = 0; m < i; m++) {

            // generate numeric
            int myindex = (int)(theAlphaNumericS.length() * Math.random());

            // add the characters
            builder.append(theAlphaNumericS.charAt(myindex));
        }

        return builder.toString();
    }
}
