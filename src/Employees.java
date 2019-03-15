import static java.lang.System.exit;

public class Employees {

    public static void main(String[] args) {
        if(args.length >= 2) {
            System.out.println("Входной файл: " + args[0]);
            System.out.println("Выходной файл: " + args[1]);
        }
        else {
            System.out.println("Не были заданны необходимые входные параметры");
            exit(-1);
        }

        EmplCompute empl = new EmplCompute(args[0], args[1]);
        empl.readFile();
        //empl.printFile();
        empl.printDepartments();
        empl.computeTransactions();
    }
}
