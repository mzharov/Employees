import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class TableFormatter {
    private String[] header;
    private List<Object[]> sTable = new LinkedList<>();
    private int[] rowMaxSize;
    private boolean[] isDigit = null;
    private String formatter = null;

    /**
     * Режимы форматирования таблицы
     */
    public static final String NO_SPACES = "no_space"; //форматирование с отступами
    public static final String SPACES = "space"; //форматирование без отступов

    private boolean spaces;

    /**
     * Конструктор форматирования таблиц
     * @param header заголовок таблицы
     * @param spaces режим записи
     */
    TableFormatter(String[] header, String spaces) {
        this.header = header;
        rowMaxSize = new int[header.length];
        getRowsMaxSize(header);
        this.spaces = spaces.equals(SPACES);
        sTable.add(header);
    }

    /**
     * Определение типа каждого столбца для задания формта вывода (строка/число)
     * @param stroke входная строка на основе которой определяется тип данных столбца
     */
    private void getRowsType(final Object[] stroke) {
        int index = 0;
        isDigit = new boolean[header.length];
        for(Object iterator:stroke) {
            if(iterator instanceof BigDecimal) {
                isDigit[index] = true;
            } else if(iterator instanceof String) {
                isDigit[index] = false;
            } else {
                isDigit[index] =false;
            }
            ++index;
        }
    }

    /**
     * Поиск максимального размера ячейки для каждого столбца
     * @param stroke входная строка
     */
    private void getRowsMaxSize(final Object[] stroke) {
        int iterator = 0;
        for(Object strokeIterator:stroke) {
            if(strokeIterator.toString().length() > rowMaxSize[iterator]) {
                rowMaxSize[iterator] = strokeIterator.toString().length();
            }
            ++iterator;
        }
    }

    /**
     * Форматирование строки на основе полученных ранее типах данных и макс. размерах ячеек
     * @return форматировщик строк
     */
    private String getTableFormatter() {
        //"%s %15s %15s %15s %-15s %-15s %-15s %-15s\r\n"
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < header.length; i++) {
            sb.append("%");
            if (!isDigit[i]) {
                sb.append("-");
            }

            sb.append(rowMaxSize[i]+5);
            sb.append("s");
            if (i != header.length - 1) {
                sb.append(" ");
            }
        }
        if(spaces){
            sb.append("\r\n");
        }
        return sb.toString();
    }

    /**
     * Форматирование строки
     * @param stroke строка, которую необходимо форматировать
     * @return отформатированная строка
     */
    private String formatString(Object[] stroke) {
        if(formatter == null) {
            formatter = getTableFormatter();
        }
        return String.format(formatter, stroke);
    }

    /**
     * Добавление строки в таблицу
     * @param stroke строка для добавления в таблицу
     */
    public void addStroke(Object[] stroke) {
        if(isDigit == null) {
            getRowsType(stroke);
        }
        getRowsMaxSize(stroke);
        sTable.add(stroke);
    }

    /**
     * Запрос отформатированной таблицы
     * @return отформатировання таблица
     */
    public List<String> getTable() {
        List<String> finalTable = new LinkedList<>();
        for (Object[] objects : sTable) {
            finalTable.add(formatString(objects));
        }
        return finalTable;
    }

    /**
     * Проверка таблицы на пустоту
     * @return true - если пустая, иначе - false
     */
    public boolean isEmpty() {return sTable.size() <= 1;}

    /**
     * Соединение таблиц
     * @param addableTable Дополнительная таблица
     * @return объединенная таблица
     */
    public List<String> addTable(TableFormatter addableTable) {
        List<String> tmpTable = this.getTable();
        tmpTable.add("\r\n");
        tmpTable.addAll(addableTable.getTable());
        return tmpTable;
    }
}
