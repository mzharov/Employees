import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CombinationJUnit4Test extends Assert {

    @Test
    public void testListProcess() {
        Combination combination = new Combination();

        /*
         * Сочетания для массива из 4-х элементов
         */
        List<int[]> testListCombination = new LinkedList<>(Arrays.asList(
                new int[]{0},
                new int[]{1},
                new int[]{2},
                new int[]{3},
                new int[]{0,1},
                new int[]{0,2},
                new int[]{0,3},
                new int[]{1,2},
                new int[]{1,3},
                new int[]{2,3},
                new int[]{0,1,2},
                new int[]{0,1,3},
                new int[]{0,2,3},
                new int[]{1,2,3},
                new int[]{0,1,2,3}));

        //Запрос списка
        List<int[]> listCombination = combination.process(4);

        Iterator<int[]> iteratorList= listCombination.iterator();
        Iterator<int[]> iteratorTestList = testListCombination.iterator();
        while (iteratorList.hasNext() && iteratorTestList.hasNext()) {
            int[] tmpList = iteratorList.next();
            int[] tmpTestList = iteratorTestList.next();

            //Сравнение длины списков
            assertEquals(tmpList.length, tmpTestList.length);

            //Сравнение каждого массива по значению
            for(int i = 0; i < tmpList.length; i++) {
                assertEquals(tmpList[i], tmpTestList[i]);
            }
        }
    }
}
