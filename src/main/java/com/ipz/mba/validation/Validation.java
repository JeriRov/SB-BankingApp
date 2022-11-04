package com.ipz.mba.validation;

public class Validation {

    public static boolean checkIpn(String ipn){
        return ipn.length() == 10;
    }

    public static boolean checkPhone(String number){
        if(number.startsWith("+")) {
            if (!number.startsWith("+380")) return false;
            number = number.substring(3);
        }
        if(number.length()!=10) return false;
        String[] codes = {"050","066","095","099","067","068","096","097","098","063","073","093"};
        for(String code : codes)
            if(number.startsWith(code))
                return true;
        return false;
    }

}
