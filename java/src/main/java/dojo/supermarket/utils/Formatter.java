package dojo.supermarket.utils;

public class Formatter {

    private Formatter() {
    }

    public static final int COLUMNS = 40;

    public static String formatLineWithWhitespace(String name, String value) {
        return name
                + " ".repeat(COLUMNS - name.length() - value.length())
                + value
                + '\n';
    }
}