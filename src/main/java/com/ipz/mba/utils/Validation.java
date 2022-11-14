package com.ipz.mba.utils;

import com.ipz.mba.models.NewCardData;

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

    //There are banks that do not follow this algorithm, but we don't work with them
    public static boolean checkCardByLuhn(String cardNumber) {
        if (cardNumber.length() < 16) return false;
        CardGenerator cardGenerator = new CardGenerator();
        return Integer.parseInt(cardNumber.substring(15, 16)) == cardGenerator.luhn(cardNumber.substring(0, 15));
    }

    public static boolean checkNewCardData(NewCardData ncd){
        if(!(ncd.getProvider().equals("Visa") || ncd.getProvider().equals("Mastercard")))
            return false;
        return ncd.getType() != null;
    }
}
