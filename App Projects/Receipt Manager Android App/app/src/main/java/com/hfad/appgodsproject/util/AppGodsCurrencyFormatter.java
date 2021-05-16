package com.hfad.appgodsproject.util;

import java.text.NumberFormat;
import java.util.Locale;

public class AppGodsCurrencyFormatter {

    public static final Locale SUPPORTED_LOCALE_CANADA = Locale.CANADA;

    public static String getCurrencyFormat(int dbAmount, Locale currentLocale) {
        double currencyAmount = convertToDoublePrice(dbAmount);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
        return currencyFormatter.format(currencyAmount);
    }

    /*
        I figure our app will only be able to support CAD/US currency. Use this method when you want
        to display prices.
     */
    public static String getCanadaCurrencyFormat(int dbAmount) {
        return getCurrencyFormat(dbAmount, SUPPORTED_LOCALE_CANADA);
    }

    public static double convertToDoublePrice(int dbAmount) {
        return dbAmount/100d;
    }

    public static int convertToIntegerPrice(double dbAmount) {
        return (int)(dbAmount * 100);
    }
}
