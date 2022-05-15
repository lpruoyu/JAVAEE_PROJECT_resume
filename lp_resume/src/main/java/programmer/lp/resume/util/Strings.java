package programmer.lp.resume.util;

public final class Strings {

    public static boolean isNull(final String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotNull(final String str) {
        return str != null && !"".equals(str);
    }

    public static String underLineCase(final String source) {
        if (isNull(source)) return source;

        final StringBuilder sb = new StringBuilder();
        sb.append(Character.toLowerCase(source.charAt(0)));
        final int len = source.length();
        for (int i = 1; i < len; i++) {
            char c = source.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

}
