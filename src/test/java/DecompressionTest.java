import org.github.zkkv.NeatCompressor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DecompressionTest {

    private static final char DELIM = NeatCompressor.DELIM;

    @Test
    void nullStringTest() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

    @Test
    void emptyStringTest() {
        String input = "";
        String expected = "";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void singleLetterTest() {
        String input = "1" + DELIM + "A";
        String expected = "A";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourSpecialCharactersTest() {
        String input = "4" + DELIM + "#";
        String expected = "####";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourSpacesTest() {
        String input = "4" + DELIM + " ";
        String expected = "    ";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void twoSameCharactersTest() {
        String input = "2" + DELIM + "A";
        String expected = "AA";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void twoDifferentCharactersTest() {
        String input = "1" + DELIM + "A"
                + "1" + DELIM + "B";
        String expected = "AB";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourSameCharactersTest() {
        String input = "4" + DELIM + "A";
        String expected = "AAAA";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void fourDifferentCharactersTest() {
        String input = "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "C"
                + "1" + DELIM + "D";
        String expected = "ABCD";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void increasingFrequencyTest() {
        String input = "1" + DELIM + "A"
                + "3" + DELIM + "B"
                + "4" + DELIM + "C"
                + "6" + DELIM + "D";
        String expected = "ABBBCCCCDDDDDD";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void decreasingFrequencyTest() {
        String input = "7" + DELIM + "A"
                + "5" + DELIM + "B"
                + "3" + DELIM + "C"
                + "2" + DELIM + "D";
        String expected = "AAAAAAABBBBBCCCDD";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sameFrequencyGroupedTogetherTest() {
        String input = "4" + DELIM + "A"
                + "4" + DELIM + "B"
                + "4" + DELIM + "C"
                + "4" + DELIM + "D";
        String expected = "AAAABBBBCCCCDDDD";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void sandwichedLettersTest() {
        String input = "1" + DELIM + "A"
                + "2" + DELIM + "B"
                + "1" + DELIM + "A";
        String expected = "ABBA";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void alternatingLettersTest() {
        String input = "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B";
        String expected = "ABABAB";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void mixedLetterStringTest() {
        String input = "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "F"
                + "3" + DELIM + "M"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "M";
        String expected = "ABFMMMABAM";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    /*@Test
    void largeStringTest() {
        // This test might not pass because of reading from file. Ignore it then.
        String input = null;
        try {
            input = Files.readString(Paths.get("src/test/resources/largeString.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String expected = "1100000" + DELIM + "A";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }*/

    @Test
    void mixedDigitsTest() {
        String input = "1" + DELIM + "1"
                + "1" + DELIM + "8"
                + "2" + DELIM + "9"
                + "1" + DELIM + "2"
                + "1" + DELIM + "8"
                + "3" + DELIM + "2"
                + "1" + DELIM + "5";
        String expected = "1899282225";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void largeConsecutiveSequenceOfDigitsTest() {
        String input = "1" + DELIM + "8"
                + "17" + DELIM + "3"
                + "1" + DELIM + "1"
                + "1" + DELIM + "7";
        String expected = "83333333333333333317";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void singleSeparatorCharacterTest() {
        String input = "1" + DELIM + DELIM;
        String expected = Character.toString(DELIM);
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    @Test
    void stringWithSeparatorCharactersTest() {
        String input = "1" + DELIM + DELIM
                + "2" + DELIM + "A"
                + "1" + DELIM + "B"
                + "2" + DELIM + DELIM
                + "1" + DELIM + "B"
                + "1" + DELIM + "A"
                + "1" + DELIM + "B";
        String expected = DELIM + "AAB" + DELIM + DELIM + "BAB";
        String actual = NeatCompressor.decompress(input);
        assertEquals(expected, actual);
    }

    /*
        Some exceptional cases.
    */

    @Test
    void twoCharacterStringExceptionTest() {
        String input = "1#" + DELIM;
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

    @Test
    void twoDigitsExceptionTest() {
        String input = "12";
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

    @Test
    void threeDigitsExceptionTest() {
        String input = "123";
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

    @Test
    void fourDigitsExceptionTest() {
        String input = "1234";
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

    @Test
    void digitIsNotFollowByDelimExceptionTest() {
        String input = "123F";
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

    @Test
    void delimIsNotFollowedByAnythingExceptionTest() {
        String input = "123" + DELIM;
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

}
