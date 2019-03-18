public class Employees {

    public static void main(String[] args) {
        if(args.length >= 2) {
            System.out.println("Входной файл: " + args[0]);
            System.out.println("Выходной файл: " + args[1]);
        }
        else {
            System.out.println("Во входных параметрах не были указаны: путь до входного и выходного файлов. " +
                    "Должно быть как минимум два входных параметра");
        }

        EmplCompute empl = new EmplCompute(args[0], args[1]);
        if(empl.readFile()) {
            empl.computeTransactions();
        }
    }
}
