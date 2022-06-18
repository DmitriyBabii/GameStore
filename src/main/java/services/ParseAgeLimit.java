package services;

import models.enums.AgeLimit;

public class ParseAgeLimit {
    private ParseAgeLimit() {

    }

    public static AgeLimit getAgeLimit(Integer value) {
        for (AgeLimit age : AgeLimit.values()) {
            if(age.getValue().equals(value)){
                return age;
            }
        }
        return null;
    }
}
