import org.junit.Assert;
import org.junit.Test;

public class formateStringToCaseJUnit4Test extends Assert {

    @Test
    public void testFormateStringToCase() {
        assertEquals("Ivanov", InputLineFormatter.formatStringToCase("ivanov"));
        assertEquals("Ivanov", InputLineFormatter.formatStringToCase("Ivanov"));
        assertEquals("Ivanov", InputLineFormatter.formatStringToCase("IVanoV"));
    }
}
