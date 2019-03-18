import java.math.BigDecimal;
import java.util.Arrays;

public class InputLineFormatter {

    /**
     * Удаление лишних пробелов из строкового массива
     * @param tmp входная строка
     * @return массив строк без лишних пробелов
     */
    public static String[] trimArray(String tmp) {
        String[] tmpArray = tmp.split(";");
        return Arrays.stream(tmpArray).map(String::trim).toArray(String[]::new);
    }

    /**
     * Проверка правильности считываемой строки необходимому формату
     * @param tmpLine входная строка id типа(int) >=0; Last Name (String); Department (String); Salary (BigDecimal) > 0
     * @return false если строка неверного формата, true - верного
     */
    public static boolean checkFileLine(final String tmpLine) {
        try {
            String[] tmp = trimArray(tmpLine);

            if(tmp[0].contains("-")
                    || tmp[1].equals("")
                    || tmp[2].equals("")
                    || ! tmp[1].matches("[A-Za-z]{2,}")
                    || (Integer.parseInt(tmp[0]) < 0)
                    || ((new BigDecimal(tmp[3]))
                    .compareTo(new BigDecimal(0)) <=0)
                    ||tmp.length != 4) {
                throw new Exception();
            }
            else {
                return true;
            }

        } catch (Exception e){
            return false;
        }
    }

    /**
     * Преобразоване входной строки к виду: первый символ - верхний регистр; остальные нижний
     * @param fString входная строка
     * @return первый символ - верхний регистр; остальные нижний: IvaNov -> Ivanov; ivanOv -> Ivanov
     */
    public static String formateStringToCase(String fString) {
        return fString.substring(0, 1).toUpperCase() + fString.substring(1).toLowerCase();
    }
}

