import org.github.zkkv.NeatCompressor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CompressionTest {

    private static final char SEPARATOR = NeatCompressor.SEPARATOR;
    //private static final char SEPARATOR = (char)0;

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
    void singleSpecialCharacterTest() {
        String input = "#";
        String expected = "#";
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
        String expected = "4" + SEPARATOR + "A";
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
        String expected = "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B"
                + "1" + SEPARATOR + "C"
                + "1" + SEPARATOR + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void increasingFrequencyTest() {
        String input = "ABBBCCCCDDDDDD";
        String expected = "1" + SEPARATOR + "A"
                + "3" + SEPARATOR + "B"
                + "4" + SEPARATOR + "C"
                + "6" + SEPARATOR + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void decreasingFrequencyTest() {
        String input = "AAAAAAABBBBBCCCDD";
        String expected = "7" + SEPARATOR + "A"
                + "5" + SEPARATOR + "B"
                + "3" + SEPARATOR + "C"
                + "2" + SEPARATOR + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sameFrequencyGroupedTogetherTest() {
        String input = "AAAABBBBCCCCDDDD";
        String expected = "4" + SEPARATOR + "A"
                + "4" + SEPARATOR + "B"
                + "4" + SEPARATOR + "C"
                + "4" + SEPARATOR + "D";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sandwichedLettersTest() {
        String input = "ABBA";
        String expected = "1" + SEPARATOR + "A"
                + "2" + SEPARATOR + "B"
                + "1" + SEPARATOR + "A";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void alternatingLettersTest() {
        String input = "ABABAB";
        String expected = "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B"
                + "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B"
                + "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void mixedLetterStringTest() {
        String input = "ABFMMMABAM";
        String expected = "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B"
                + "1" + SEPARATOR + "F"
                + "3" + SEPARATOR + "M"
                + "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B"
                + "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "M";
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
        String expected = "1100000" + SEPARATOR + "A";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void mixedDigitsTest() {
        String input = "1899282225";
        String expected = "1" + SEPARATOR + "1"
                + "1" + SEPARATOR + "8"
                + "2" + SEPARATOR + "9"
                + "1" + SEPARATOR + "2"
                + "1" + SEPARATOR + "8"
                + "3" + SEPARATOR + "2"
                + "1" + SEPARATOR + "5";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void largeConsecutiveSequenceOfDigitsTest() {
        String input = "83333333333333333317";
        String expected = "1" + SEPARATOR + "8"
                + "17" + SEPARATOR + "3"
                + "1" + SEPARATOR + "1"
                + "1" + SEPARATOR + "7";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void stringWithSeparatorCharactersTest() {
        String input = SEPARATOR + "AAB" + SEPARATOR + SEPARATOR + "BAB";
        String expected = "1" + SEPARATOR + SEPARATOR
                + "2" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B"
                + "2" + SEPARATOR + SEPARATOR
                + "1" + SEPARATOR + "B"
                + "1" + SEPARATOR + "A"
                + "1" + SEPARATOR + "B";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }
}
