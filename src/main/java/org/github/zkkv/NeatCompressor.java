package org.github.zkkv;

public class NeatCompressor {

    private NeatCompressor() { };

    public final static char SEPARATOR = (char) 0;

    public static String compress(final String uncompressed) {
        if (uncompressed == null) throw new IllegalArgumentException("Input string cannot be null");

        if (uncompressed.length() <= 3) return uncompressed;

        int N = uncompressed.length();
        int l = 0;
        int r = 1;

        StringBuilder result = new StringBuilder();
        result.append(uncompressed.charAt(l));
        int counter = 1;

        while (r < N) {
            if (uncompressed.charAt(l) == uncompressed.charAt(r)) {
                counter++;
            } else {
                result.append(SEPARATOR);
                result.append(counter);
                result.append(uncompressed.charAt(r));
                counter = 1;
                l = r;
            }
            r++;
        }
        result.append(SEPARATOR);
        result.append(counter);

        return result.toString();
    }

    public static String decompress(final String compressed) {
        if (compressed == null) throw new IllegalArgumentException("Input string cannot be null");

        return "";
    }
}
