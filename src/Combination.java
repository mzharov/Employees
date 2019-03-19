import java.util.ArrayList;
import java.util.List;

public class Combination {

    private List<int[]> indexList = new ArrayList<>();

    /**
     * Получение размера сочетаниемого массива
     * @param size размер
     * @return список индексов элементов (комбинаторное сочетание)
     */
    public List<int[]> process(int size) {
        int[] input = new int[size];
        for(int i = 1; i <= input.length; i++) {
            doCombination(size, i);
        }
        //subsets.forEach(n-> System.out.println(Arrays.toString(n)));
        return indexList;
    }

    /**
     * @param size размер последовательности
     * @param k количество элементов для сочетания
     */
    private void doCombination(final int size, int k) {
        int[] s = new int[k];

        if (k <= size) {

            //Добавление одиночных вариантов
            for (int i = 0; (s[i] = i) < k - 1; i++);
            indexList.add(getSubset(s));

            //Поиск множественных сочетаний
            for(;;) {
                int i;

                for (i = k - 1; i >= 0 && s[i] == size - k + i; i--);
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

    private static int[] getSubset(int[] subset) {
        int[] result = new int[subset.length];
        for (int i = 0; i < subset.length; i++)
            //result[i] = input[subset[i]];
            result[i] = subset[i];
        return result;
    }
}
