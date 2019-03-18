import java.math.BigDecimal;

public class InputLineFormatter {
    /**
     * Проверка правильности считываемой строки необходимому формату
     * @param tmpLine входная строка id типа(int) >=0; Last Name (String); Department (String); Salary (BigDecimal) > 0
     * @return false если строка неверного формата, true - верного
     */
    public static boolean checkFileLine(final String tmpLine) {
        try {
            String[] tmp = tmpLine.split(";");

            if(tmp[0].contains("-")
                    || tmp[1].equals("")
                    || tmp[2].equals("")
                    || ! tmp[1].trim().matches("[A-Za-z]{2,}")
                    || (Integer.parseInt(tmp[0].trim()) < 0)
                    || ((new BigDecimal(tmp[3].trim()))
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

