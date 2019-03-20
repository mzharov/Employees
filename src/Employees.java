public class Employees {

    public static void main(String[] args) {
        if(args.length >= 2) {
            System.out.println("Входной файл: " + args[0]);
            System.out.println("Выходной файл: " + args[1]);
        }
        else {
            System.out.println("Должно быть как минимум два входных параметра (путь до входного и выходного файлов)");
        }

        EmplCompute empl = new EmplCompute(args[0], args[1]);

        /*
         * Отслеживаем состояние чтения и выводим соотвествующие сообщения
         */

        int readCondition = empl.readFile();
        switch (readCondition) {
            case -2: {
                System.out.println("В файле " + empl.getInputFile() + " не были найдены необходимые данные.");
                break;
            }
            case -1: {
                System.out.println("Ошибка в ходе чтения файла " + empl.getInputFile());
                break;
            }
            case 0: {
                System.out.println("Файл " + empl.getInputFile() + " не найден.");
            }
            case 1: {
                /*
                 * Вывод списка строк с ошибками
                 */
                if((empl.getErrorSet().size() > 0)) {
                    System.out.println("В данных строках файла " + empl.getInputFile() + " были обнаружены ошибки и они не были считаны:");
                    empl.getErrorSet().forEach(e->System.out.print(e + " "));
                    System.out.println();
                }

                /*
                 * Отслеживаем состояние поиска и записи в файл
                 */
                int transCondition = empl.computeTransactions();
                switch (transCondition) {
                    case -2: {
                        System.out.println("Нет сотрудников удовлетворяющих условиям");
                        break;
                    }
                    case -1: {
                        System.out.println("Ошибка в ходе записи в файл");
                        break;
                    }
                    case 1: {
                        System.out.println("Данные успешно записаны в файл " + empl.getOutputFile());
                        break;
                    }
                }
                break;
            }
        }
    }
}
