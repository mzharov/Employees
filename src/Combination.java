import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Combination {

    private final List<int[]> indexList = new LinkedList<>();

    /**
     * Вызов поиска комбинаций элементов массива
     * @param size размер
     * @return список индексов элементов (комбинаторное сочетание)
     */
    public List<int[]> process(int size) {
        for(int i = 1; i <= size; i++) {
            doCombination(size, i);
        }
        return indexList;
    }

    /**
     * @param size размер последовательности
     * @param k количество элементов для сочетания
     */
    private void doCombination(final int size, int k) {
        int[] s = new int[k];

        if (k <= size) {

            //Начальные сочетания (копия входного массива)
            for (int i = 0; i < k - 1; i++) {
                s[i] = i;
            }

            indexList.add(getSubset(s));

            //Поиск остальных сочетаний
            while (true) {
                int i;

                for (i = k - 1; i >= 0 && i == size - k + i; i--) {
                    s[i] = size - k + i;
                }
                if (i < 0) {
                    break;
                }
                s[i]++;
                for (++i; i < k; i++) {
                    s[i] = s[i - 1] + 1;
                }
                indexList.add(getSubset(s));
            }
        }
    }

    /**
     * Копирования массива
     * @param subset входной массив
     * @return копия массива
     */
    private static int[] getSubset(int[] subset) {
        return Arrays.copyOf(subset, subset.length);
    }
}
