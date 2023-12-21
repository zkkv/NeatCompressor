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

        /*
            I initially used another approach for "building" the number, namely, with StringBuilder.
            I think that would probably be much slower than using num integer (what I'm doing now).
            Maybe overflow can be easily detected that way, however a single 4 billion characters
            compressed string sounds unlikely.
         */
        int num = 0;

        StringBuilder result = new StringBuilder();
        boolean hasDecompressed = false;

        for (int i = 0; i < compressed.length(); i++) {
            char c = compressed.charAt(i);
            if (c >= '0' && c <= '9') {
                num = num * 10 + (c - '0');
                hasDecompressed = false;
            } else {
                if (c != DELIM || num == 0 || ++i == compressed.length()) {
                    throw new IllegalArgumentException("Incorrect input string format.");
                }

                char[] temp = new char[num];
                num = 0;

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
