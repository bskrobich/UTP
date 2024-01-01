package zad1;

import java.util.Locale;

public class CountryInterpreter {
    public static String interpret(String country, Locale lFrom, Locale lTo) {

        Locale[] availableLocales = Locale.getAvailableLocales();

        for (Locale locale : availableLocales) {
            if (locale.getDisplayCountry(lFrom).equals(country)) {
                return locale.getDisplayCountry(lTo);
            }
        }
        return "";
    }
}
