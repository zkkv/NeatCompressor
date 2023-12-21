package org.github.zkkv;

public class NeatCompressor {

    private NeatCompressor() { };

    public final static char DELIM = '?';

    public static String compress(final String uncompressed) {
        if (uncompressed == null) throw new IllegalArgumentException("Input string cannot be null");

        if (uncompressed.length() == 0) return "";

        StringBuilder result = new StringBuilder();
        int l = 0;
        int counter = 1;

        for (int r = 1; r < uncompressed.length(); r++) {
            if (uncompressed.charAt(l) == uncompressed.charAt(r)) {
                counter++;
            } else {
                result.append(counter);
                result.append(DELIM);
                result.append(uncompressed.charAt(l));
                counter = 1;
                l = r;
            }
        }
        result.append(counter);
        result.append(DELIM);
        result.append(uncompressed.charAt(l));

        return result.toString();
    }

    public static String decompress(final String compressed) {
        if (compressed == null) throw new IllegalArgumentException("Input string cannot be null");

        if (compressed.length() == 0) return "";

        for (int r = 1; r < compressed.length(); r++) {

        }
        return null;
    }
}
