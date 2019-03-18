import org.junit.Assert;
import org.junit.Test;

public class checkFileLineJUnit4Test extends Assert{
    @Test
    public void testCheckFileLine() {

        /*
         *Правильные входные данные
         */
        assertTrue(InputLineFormatter.checkFileLine("0;Ivanov;IT;43000"));
        assertTrue(InputLineFormatter.checkFileLine(" 1; Ivanovich ; ITS ;            143000 "));
        assertTrue(InputLineFormatter.checkFileLine("12;Ivanov;14;15"));
        assertTrue(InputLineFormatter.checkFileLine("0;Ivanov;IT;12000;"));

        /*
         *Отрицательные числовые данные
         */
        assertFalse(InputLineFormatter.checkFileLine("-0;Ivanov;IT;43000"));
        assertFalse(InputLineFormatter.checkFileLine("0;Ivanov;IT;-43000"));
        assertFalse(InputLineFormatter.checkFileLine("-12;Ivanov;IT;-43000"));

        /*
         *Непраивильные входные данные
         */
        assertFalse(InputLineFormatter.checkFileLine(";0;Ivanov;IT;12000"));
        assertFalse(InputLineFormatter.checkFileLine("h;Ivanov;IT;43000"));
        assertFalse(InputLineFormatter.checkFileLine(""));
        assertFalse(InputLineFormatter.checkFileLine("0;Ivanov;IT;43000;122"));
        assertFalse(InputLineFormatter.checkFileLine("0;Ivanov;43000"));
        assertFalse(InputLineFormatter.checkFileLine("0;Ivanov;;43000"));
        assertFalse(InputLineFormatter.checkFileLine("0;;IT;43000"));
        assertFalse(InputLineFormatter.checkFileLine("0;;;43000"));
        assertFalse(InputLineFormatter.checkFileLine(";;IT;43000"));
        assertFalse(InputLineFormatter.checkFileLine("0;;IT;"));
        assertFalse(InputLineFormatter.checkFileLine("0;;Ivanov;IT;12000;"));
        assertFalse(InputLineFormatter.checkFileLine(";;;"));
        assertFalse(InputLineFormatter.checkFileLine("12e;Ivanov;IT;43000e"));

        /*
         * Проверка фамилии
         */
        assertFalse(InputLineFormatter.checkFileLine("12;13;14;15"));
        assertTrue(InputLineFormatter.checkFileLine("12;Li;14;15"));
        assertTrue(InputLineFormatter.checkFileLine("1; Ivanov; ITS; 143000"));
        assertTrue(InputLineFormatter.checkFileLine("1; Iv; ITS; 143000"));
        assertFalse(InputLineFormatter.checkFileLine("1; I; ITS; 143000"));
        assertFalse(InputLineFormatter.checkFileLine("1; i; ITS; 143000"));
        assertTrue(InputLineFormatter.checkFileLine("1; vanov; ITS; 143000"));
        assertFalse(InputLineFormatter.checkFileLine("1; Ivanov Ivan; ITS; 143000"));
        assertTrue(InputLineFormatter.checkFileLine("1; Ivanov; IT Department; 143000"));
        assertTrue(InputLineFormatter.checkFileLine("1; IVanov; IT; 143000"));
    }
}
