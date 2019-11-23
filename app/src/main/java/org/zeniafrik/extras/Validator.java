package org.zeniafrik.extras;

import java.util.regex.Pattern;

public class Validator {

    protected static final Pattern emailPattern = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);

    public static boolean isValidMTN(String numberPrefix) {
        final String[] prefixes = {"024", "054", "055"};
        return checkNumberValidity(numberPrefix, prefixes);

    }

    public static boolean isValidVodafone(String numberPrefix) {
        final String[] prefixes = {"020", "050"};
        return checkNumberValidity(numberPrefix, prefixes);

    }

    public static boolean isValidTigoAirtel(String numberPrefix) {
        final String[] prefixes = {"027", "057", "056", "026"};
        return checkNumberValidity(numberPrefix, prefixes);

    }

    public static boolean isValidGlo(String numberPrefix) {
        return numberPrefix.equals("023");

    }

    public static boolean isValidGhanaNumber(String number) {
        if ((number.isEmpty()) || isLessThan(number, 3)) return false;

        String prefix = number.substring(0, 3);
        return (isValidGlo(prefix) || isValidMTN(prefix) || isValidTigoAirtel(prefix) || isValidVodafone(prefix)) && number.length() == 10;
    }

    private static boolean checkNumberValidity(String prefix, String[] prefixes) {
        boolean validity;
        int i = 0, maxLoop = prefixes.length;

        do {
            validity = prefixes[i].equals(prefix);
            i++;
        } while (!validity && i < maxLoop);
        return validity;
    }

    public static boolean isLessThan(String text, int size) {
        return text.length() < size;
    }

    public static boolean isGreaterThan(String text, int size) {
        return text.length() > size;
    }

    public static boolean isValidEmail(String email) {
        return emailPattern.matcher(email).matches();
    }
}
