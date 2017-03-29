package parkinggo.com.util;

public class StringHelper {

    public static String join(final String joiner, final Object firstObject, final Object secondObject,
                              final Object... objects) {
        return "";
//        return Joiner.on(joiner).skipNulls().join(firstObject, secondObject, objects);
    }

    public static boolean equal(String lhs, String rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equals(rhs);
    }

    public static boolean equalIgnoreCase(String lhs, String rhs) {
        return lhs == null && rhs == null || lhs != null && rhs != null && lhs.equalsIgnoreCase(rhs);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
