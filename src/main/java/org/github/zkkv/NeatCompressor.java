package org.github.zkkv;

import java.util.Arrays;

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

        if (compressed.length() < 3) throw new IllegalArgumentException("Incorrect input string format.");

        StringBuilder numBuilder = new StringBuilder();
        StringBuilder result = new StringBuilder();
        boolean hasDecompressed = false;

        for (int i = 0; i < compressed.length(); i++) {
            char c = compressed.charAt(i);
            if (c >= '0' && c <= '9') {
                numBuilder.append(c);
                hasDecompressed = false;
            } else {
                if (c != DELIM) {
                    throw new IllegalArgumentException("Incorrect input string format.");
                }

                int count = Integer.parseInt(numBuilder.toString());
                numBuilder.setLength(0);
                char[] temp = new char[count];

                i++;
                if (i == compressed.length()) {
                    throw new IllegalArgumentException("Incorrect input string format.");
                }

                Arrays.fill(temp, compressed.charAt(i));
                result.append(temp);
                hasDecompressed = true;
            }
        }

        if (!hasDecompressed) {
            throw new IllegalArgumentException("Incorrect input string format.");
        }

        return result.toString();
    }
}
