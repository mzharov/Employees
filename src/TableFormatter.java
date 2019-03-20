import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TableFormatter {
    private String[] header;
    private List<Object[]> sTable = new LinkedList<>();
    private int[] rowMaxSize;
    private boolean[] isDigit = null;
    private String formatter = null;

    TableFormatter(String[] header) {
        this.header = header;
        rowMaxSize = new int[header.length];
        getRowsMaxSize(header);
        sTable.add(header);
    }

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
    private void getRowsMaxSize(final Object[] stroke) {
        int iterator = 0;
        for(Object strokeIterator:stroke) {
            if(strokeIterator.toString().length() > rowMaxSize[iterator]) {
                rowMaxSize[iterator] = strokeIterator.toString().length();
            }
            ++iterator;
        }
    }
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
        sb.append("\r\n");
        return sb.toString();
    }

    private String formateString(Object[] stroke) {
        if(formatter == null) {
            formatter = getTableFormatter();
        }
        return String.format(formatter, stroke);
    }
    public void addStroke(Object[] stroke) {
        if(isDigit == null) {
            getRowsType(stroke);
        }
        getRowsMaxSize(stroke);
        sTable.add(stroke);
    }

    public List<String> getTable() {
        List<String> finalTable = new LinkedList<>();
        Iterator<Object[]> sIterator = sTable.iterator();
        while (sIterator.hasNext()) {
            finalTable.add(formateString(sIterator.next()));
        }
        return finalTable;
    }
    public boolean isEmpty() {return sTable.isEmpty();}
}
