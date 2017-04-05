/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 04/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.util.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String SPECIAL_CHARS = "\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]";
    private static final String VALID_CHARS = "(\\\\.)|[^\\s" + SPECIAL_CHARS + "]";
    private static final String QUOTED_USER = "(\"(\\\\\"|[^\"])*\")";
    private static final String WORD = "((" + VALID_CHARS + "|')+|" + QUOTED_USER + ")";

    private static final String EMAIL_REGEX = "^\\s*?(.+)@(.+?)\\s*$";
    private static final String USER_REGEX = "^\\s*" + WORD + "(\\." + WORD + ")*$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern USER_PATTERN = Pattern.compile(USER_REGEX);

    private static final int MAX_USERNAME_LEN = 64;

    /**
     * Singleton instance of this class, which
     * doesn't consider local addresses as valid.
     */
    private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

    /**
     * Returns the Singleton instance of this validator.
     *
     * @return singleton instance of this validator.
     */
    public static EmailValidator getInstance() {
        return EMAIL_VALIDATOR;
    }

    /**
     * <p>Checks if a field has a valid e-mail address.</p>
     *
     * @param email The value validation is being performed on.  A <code>null</code>
     *              value is considered invalid.
     * @return true if the email address is valid.
     */
    public boolean isValid(String email) {
        if (email == null) {
            return false;
        }

        if (email.endsWith(".")) { // check this first - it's cheap!
            return false;
        }

        // Check the whole email address structure
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches()) {
            return false;
        }
        return isValidUser(emailMatcher.group(1));
    }

    /**
     * Returns true if the user component of an email address is valid.
     *
     * @param user being validated
     * @return true if the user name is valid.
     */
    protected boolean isValidUser(String user) {
        if (user == null || user.length() > MAX_USERNAME_LEN) {
            return false;
        }
        return USER_PATTERN.matcher(user).matches();
    }

}