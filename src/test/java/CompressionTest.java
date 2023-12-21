import org.github.zkkv.NeatCompressor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CompressionTest {

    private static final char SEPARATOR = NeatCompressor.SEPARATOR;

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
        String expected = "A" + SEPARATOR + "4";
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
        String expected = "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "1"
                + "C" + SEPARATOR + "1"
                + "D" + SEPARATOR + "1";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void increasingFrequencyTest() {
        String input = "ABBBCCCCDDDDDD";
        String expected = "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "3"
                + "C" + SEPARATOR + "4"
                + "D" + SEPARATOR + "6";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void decreasingFrequencyTest() {
        String input = "AAAAAAABBBBBCCCDD";
        String expected = "A" + SEPARATOR + "7"
                + "B" + SEPARATOR + "5"
                + "C" + SEPARATOR + "3"
                + "D" + SEPARATOR + "2";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sameFrequencyGroupedTogetherTest() {
        String input = "AAAABBBBCCCCDDDD";
        String expected = "A" + SEPARATOR + "4"
                + "B" + SEPARATOR + "4"
                + "C" + SEPARATOR + "4"
                + "D" + SEPARATOR + "4";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sandwichedLettersTest() {
        String input = "ABBA";
        String expected = "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "2"
                + "A" + SEPARATOR + "1";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void alternatingLettersTest() {
        String input = "ABABAB";
        String expected = "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "1"
                + "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "1"
                + "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "1";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }

    @Test
    void mixedLetterStringTest() {
        String input = "ABFMMMABAM";
        String expected = "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "1"
                + "F" + SEPARATOR + "1"
                + "M" + SEPARATOR + "3"
                + "A" + SEPARATOR + "1"
                + "B" + SEPARATOR + "1"
                + "A" + SEPARATOR + "1"
                + "M" + SEPARATOR + "1";
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
        String expected = "A" + SEPARATOR + "1100000";
        String actual = NeatCompressor.compress(input);
        assertEquals(expected, actual);
    }
}
