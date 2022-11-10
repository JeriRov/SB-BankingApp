package com.ipz.mba.validation;

import java.util.regex.Pattern;

public class Validation {

    public static boolean checkIpn(String ipn) {
        return ipn.length() == 10;
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("+")) {
            if (!phoneNumber.startsWith("+380")) return false;
            phoneNumber = phoneNumber.substring(3);
        }
        if (phoneNumber.length() != 10) return false;
        String[] codes = {"050", "066", "095", "099", "067", "068", "096", "097", "098", "063", "073", "093"};
        for (String code : codes)
            if (phoneNumber.startsWith(code))
                return true;
        return false;
    }

    public static boolean checkPassportNumber(String passportNumber) {
        String oldPassportFormat = "^[А-ЯA-Z]{2}\\d{6}$";
        String twentySixteenPassportFormat = "^\\d{13}$";
        String twentyTwentyPassportFormat = "^\\d{9}$";

        return Pattern.matches(oldPassportFormat, passportNumber)
                || Pattern.matches(twentySixteenPassportFormat, passportNumber)
                || Pattern.matches(twentyTwentyPassportFormat, passportNumber);
    }
}
