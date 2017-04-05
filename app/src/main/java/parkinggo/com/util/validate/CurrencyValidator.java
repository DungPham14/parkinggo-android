/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 04/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.util.validate;


import java.util.regex.Pattern;

public class CurrencyValidator {
    private static final String CURRENCY_REGEX = "[0-9]+(\\.[0-9][0-9]?)?";
    private static final Pattern CURRENCY_PATTERN = Pattern.compile(CURRENCY_REGEX);

    public static boolean isValidate(String currency) {
        return CURRENCY_PATTERN.matcher(currency).matches();
    }
}
