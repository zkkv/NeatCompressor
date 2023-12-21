import org.github.zkkv.NeatCompressor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompressionTest {

    @Test
    void compressNull() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> NeatCompressor.compress(input));
    }

}
