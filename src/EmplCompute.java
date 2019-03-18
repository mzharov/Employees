import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class EmplCompute {

    private String inputFile;
    private String outputFile;
    private List<EmplPerson> emplPersons = new LinkedList<>();
    private Map<String, Departments> departments = new ConcurrentHashMap<>();
    private List<Integer> errorSet = new LinkedList<>();

    private final String[] tabs = {"empl_id", "empl_name" , "empl_salary", "new_dept", "prev_dept",
            "new_dept_avg", "prev_dept_avg",
            "new_dept_old_avg", "prev_dept_old_avg"};
    private final String[] tabsDepartments = {"departmnet_name", "avg_salary"};


    EmplCompute(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public String getInputFile() {return inputFile;}
    public String getOutputFile() {return outputFile;}
    public List<Integer> getErrorSet() {return errorSet;}

    /**
     * Считывание данных из файла и сохранение в ArrayList
     * @return -2 данные не были найдены; -1 - ошибка чтения; 0 - файл не найден;  1 - файл прочитан;
     */
    public int readFile() {
        int errorIterator = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = "";

            while ((line = br.readLine()) != null) {
                //String[] tmp = line.split(";");
                ++errorIterator;
                if(InputLineFormatter.checkFileLine(line)) {
                    String[] tmp = InputLineFormatter.trimArray(line);
                    emplPersons.add(new EmplPerson(Integer.parseInt(tmp[0]),
                            tmp[1],
                            new BigDecimal(tmp[3])));

                    if(departments.containsKey(tmp[2])) {
                        /*
                         * Если объект для департамента уже создан, то нового сотрудника добавляем туда
                         */
                        departments.get(tmp[2]).addEmpl(new EmplPerson(Integer.parseInt(tmp[0]),
                                tmp[1],
                                new BigDecimal(tmp[3])));
                    } else {
                        /*
                         * Если департамент встчечается в первый раз,
                         * создаем новый объект и добавляем сотрудника туда
                         */
                        Departments dpt = new Departments(tmp[2]);
                        dpt.addEmpl(new EmplPerson(Integer.parseInt(tmp[0]),
                                tmp[1],
                                new BigDecimal(tmp[3])));
                        departments.putIfAbsent(tmp[2], dpt);
                    }
                } else {
                    errorSet.add(errorIterator);
                }
            }
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            return -1;
        }
        /*
         * Если в ходе чтения так и не было найдено строк в правильном формате
         * возвращаем -2
         */
        if(departments.size() <=0) {
            return -2;
        }
        /*
         *возвращаем 1 в случае успешного чтения файла
         */
        return 1;
    }


    public void printFile() {
        emplPersons.forEach(n->System.out.println(n.toString()));
    }


    /**
     * Выводим список департаментов
     */

    public void printDepartments() {
        departments.forEach((k,v)->System.out.println(v.toString()));
    }


    /**
     * @return формат вывода таблицы переводов
     */
    private String getMainTableFormatter() {
        //"%s %15s %15s %15s %-15s %-15s %-15s %-15s\r\n"
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < tabs.length; i++) {
            sb.append("%");
            if(i == 1 || i == 3 || i == 4) {
                sb.append("-");
            }
            appendFormatter(sb, i, tabs);
        }
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * @return формат вывода таблицы департаментов
     */
    private String getFormatter() {
        //"%s %15s %15s %15s %-15s %-15s %-15s %-15s\r\n"
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < tabsDepartments.length; i++) {
            sb.append("%");
            if(i == 0) {
                sb.append("-");
            }
            appendFormatter(sb, i, tabsDepartments);
        }
        sb.append("\r\n");
        return sb.toString();
    }

    private void appendFormatter(StringBuilder sb, int i, final String[] tabs) {
        sb.append((tabs[i].length()+1));
        sb.append("s");
        if(i!=tabs.length-1) {
            sb.append(" ");
        }
    }

    /**
     * Находим сотрудников, которые удовлетворяют условию
     * @return  -2 - нет сотрудников удовлетворяющим условию; -1 - ошибка в ходе записи; 1 - данные успешно записаны в файл
     */
    public int computeTransactions() {
        List<String> tmpArray = new ArrayList<>();

        Iterator<Map.Entry<String,Departments>> deptIter =
                departments.entrySet().iterator();
        while (deptIter.hasNext()) {
            Departments depts = deptIter.next().getValue();
            //Проходим по всем сотрудникам и сравниваем их зарплату с средней зарплатой по департаменту
            Iterator<EmplPerson> emplsIter = depts.getEmployeersList().iterator();
            while (emplsIter.hasNext()) {
                EmplPerson emplPerson = emplsIter.next();
                //Если зарплата меньше среднего, то ищем департамент, где она больше среднего
                if(emplPerson.getSalary().compareTo(depts.getAvgSalary())  < 0) {
                    Iterator<Map.Entry<String, Departments>> deptIterInner =
                            departments.entrySet().iterator();
                    while (deptIterInner.hasNext()) {
                        Departments deptsInner = deptIterInner.next().getValue();
                        if(!depts.getDptName().equals(deptsInner.getDptName())) {
                            if(emplPerson.getSalary().compareTo(deptsInner.getAvgSalary())  > 0) {
                                tmpArray.add(String.format(getMainTableFormatter(),
                                        emplPerson.getId(),
                                        emplPerson.getLastName(),
                                        emplPerson.getSalary(),
                                        deptsInner.getDptName(),
                                        depts.getDptName(),
                                        deptsInner.getTAvgSalary(emplPerson.getSalary()),
                                        depts.getTAvgSalary(emplPerson.getSalary().negate()),
                                        deptsInner.getAvgSalary(),
                                        depts.getAvgSalary()));
                            }
                        }
                    }
                }
            }
        }

        //Если были найдены сотрудники, записываем полученный массив данных в выходной файл
        if(tmpArray.size() > 0) {
           try {
               tmpArray.add("All departments average salary:");
               tmpArray.add("");
               tmpArray.add(String.format(getFormatter(), tabsDepartments));
               departments.forEach((k,v) -> tmpArray.add(String.format(getFormatter(), v.getDptName(), v.getAvgSalary())));
               tmpArray.add(0, String.format(getMainTableFormatter(), tabs));
               Files.write(Paths.get(outputFile), tmpArray, Charset.defaultCharset());
           }
           catch (Exception e) {
               e.printStackTrace();
               return -1;
           }
        } else {
            return -2;
        }
        return 1;
    }

}
