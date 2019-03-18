public class Employees {

    public static void main(String[] args) {
        if(args.length >= 2) {
            System.out.println("Входной файл: " + args[0]);
            System.out.println("Выходной файл: " + args[1]);
        }
        else {
            System.out.println("Не были заданы необходимые входные параметры: путь до входного и выходного файлов.");
        }

        EmplCompute empl = new EmplCompute(args[0], args[1]);
        if(empl.readFile()) {
            empl.printFile();
            empl.printDepartments();
            empl.computeTransactions();
        }
    }
}
