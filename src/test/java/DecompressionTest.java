import org.github.zkkv.NeatCompressor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DecompressionTest {

    @Test
    void decompressNull() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.decompress(input));
    }

}
