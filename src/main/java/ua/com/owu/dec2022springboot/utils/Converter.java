package ua.com.owu.dec2022springboot.utils;

import org.bson.types.ObjectId;


public class Converter {
    public static ObjectId convertToObjectId(String inputString) {

        if (inputString == null || inputString.isEmpty()) {
            throw new IllegalArgumentException("String cannot be empty or null");
        }

        if (inputString.length() == 24) {
            return new ObjectId(inputString);
        }

        StringBuilder stringBuilder = new StringBuilder(inputString);
        while (stringBuilder.length() < 24) {
            stringBuilder.append('0');
        }

        return new ObjectId(stringBuilder.toString());
    }

}
