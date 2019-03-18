import org.junit.Assert;
import org.junit.Test;

public class formateStringToCaseJUnit4Test extends Assert {

    @Test
    public void testFormateStringToCase() {
        assertEquals("Ivanov", InputLineFormatter.formateStringToCase("ivanov"));
        assertEquals("Ivanov", InputLineFormatter.formateStringToCase("Ivanov"));
        assertEquals("Ivanov", InputLineFormatter.formateStringToCase("IVanoV"));
    }
}
