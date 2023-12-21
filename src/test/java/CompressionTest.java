import org.github.zkkv.NeatCompressor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CompressionTest {

    private static final char DELIM = NeatCompressor.DELIM;

    @Test
    void nullStringTest() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.compress(input));
    }

    @Test
    void emptyStringTest() {
        String input = "";
        String expected = "";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void singleLetterTest() {
        String input = "A";
        String expected = "A";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourSpecialCharactersTest() {
        String input = "####";
        String expected = "4" + DELIM + "#";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourSpacesTest() {
        String input = "    ";
        String expected = "4" + DELIM + " ";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void twoSameCharactersTest() {
        String input = "AA";
        String expected = "AA";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void threeSameCharactersTest() {
        String input = "AAA";
        String expected = "AAA";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourSameCharactersTest() {
        String input = "AAAA";
        String expected = "4" + DELIM + "A";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void threeDifferentCharactersTest() {
        String input = "ABC";
        String expected = "ABC";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourDifferentCharactersTest() {
        String input = "ABCD";
        String expected = "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "C"
                + "1" + DELIM + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void increasingFrequencyTest() {
        String input = "ABBBCCCCDDDDDD";
        String expected = "1" + DELIM + "A"
                + "3" + DELIM + "B"
                + "4" + DELIM + "C"
                + "6" + DELIM + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void decreasingFrequencyTest() {
        String input = "AAAAAAABBBBBCCCDD";
        String expected = "7" + DELIM + "A"
                + "5" + DELIM + "B"
                + "3" + DELIM + "C"
                + "2" + DELIM + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sameFrequencyGroupedTogetherTest() {
        String input = "AAAABBBBCCCCDDDD";
        String expected = "4" + DELIM + "A"
                + "4" + DELIM + "B"
                + "4" + DELIM + "C"
                + "4" + DELIM + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sandwichedLettersTest() {
        String input = "ABBA";
        String expected = "1" + DELIM + "A"
                + "2" + DELIM + "B"
                + "1" + DELIM + "A";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void alternatingLettersTest() {
        String input = "ABABAB";
        String expected = "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void mixedLetterStringTest() {
        String input = "ABFMMMABAM";
        String expected = "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "F"
                + "3" + DELIM + "M"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "M";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void largeStringTest() {
        // This test might not pass because of reading from file. Ignore it then.
        String input = null;
        try {
            input = Files.readString(Paths.get("src/test/resources/largeString.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String expected = "1100000" + DELIM + "A";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void mixedDigitsTest() {
        String input = "1899282225";
        String expected = "1" + DELIM + "1"
                + "1" + DELIM + "8"
                + "2" + DELIM + "9"
                + "1" + DELIM + "2"
                + "1" + DELIM + "8"
                + "3" + DELIM + "2"
                + "1" + DELIM + "5";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void largeConsecutiveSequenceOfDigitsTest() {
        String input = "83333333333333333317";
        String expected = "1" + DELIM + "8"
                + "17" + DELIM + "3"
                + "1" + DELIM + "1"
                + "1" + DELIM + "7";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void stringWithSeparatorCharactersTest() {
        String input = DELIM + "AAB" + DELIM + DELIM + "BAB";
        String expected = "1" + DELIM + DELIM
                + "2" + DELIM + "A"
                + "1" + DELIM + "B"
                + "2" + DELIM + DELIM
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }
}
