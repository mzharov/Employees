import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.exit;


public class EmplCompute {

    private String inputFile;
    private String outputFile;
    //private List<EmplPerson> emplPersons = new ArrayList<>();
    private List<Departments> departments = new LinkedList<>();


    EmplCompute(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    /**
     * Считывание данных из файла и сохранение в ArrayList
     */
    public void readFile() {

        try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] tmp = line.split(";");
                /*
                 * Если объект для департамента уже создан, то нового сотрудника добавляем туда
                 */
                Iterator<Departments> iter = departments.iterator();
                boolean hasDept = false;
                while (iter.hasNext()) {
                    Departments dept = iter.next();
                    if(dept.getDptName().equals(tmp[2].trim())) {
                        dept.addEmpl(new EmplPerson(Integer.parseInt(tmp[0].trim()),
                                tmp[1].trim(),
                                Double.parseDouble(tmp[3].trim())));


                        hasDept = true;
                        break;
                    }
                }
                /*
                 * Если департамент встчечается в первый раз,
                 * создаем новый объект и добавляем сотрудника туда
                 */
                if(!hasDept) {
                    Departments dpt = new Departments(tmp[2].trim());
                    dpt.addEmpl(new EmplPerson(Integer.parseInt(tmp[0].trim()),
                            tmp[1].trim(),
                            Integer.parseInt(tmp[3].trim())));
                    departments.add(dpt);
                }
            }
            /*
             * Считаем среднюю зарплату по департаментам
             */
            departments.forEach(n->n.computeAvgSalary());
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
            exit(-1);
        } catch (IOException e) {
            System.out.println("Ошибка в ходе чтения.");
            exit(-1);
        } catch (Exception e) {
            System.out.println("Ошибка в ходе парсинга файла.");
            e.printStackTrace();
            exit(-1);
        }
    }

    /*
    public void printFile() {
        Iterator<EmplPerson> iter = emplPersons.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toString());
        }
    }*/


    /**
     * Выводим список департаментов
     */

    public void printDepartments() {
        departments.forEach(n->System.out.println(n.toString()));
    }


    /**
     * Находим сотрудников, которые удовлетворяют условию
     */
    public void computeTransactions() {
        List<String> tmpArray = new ArrayList<>();

        Iterator<Departments> deptIter = departments.iterator();
        while (deptIter.hasNext()) {
            Departments depts = deptIter.next();
            //Проходим по всем сотрудникам и сравниваем их зарплату с средней зарплатой по департаменту
            Iterator<EmplPerson> emplsIter = depts.getEmployeersList().iterator();
            while (emplsIter.hasNext()) {
                EmplPerson emplPerson = emplsIter.next();
                //Если зарплата меньше среднего, то ищем департамент, где она больше среднего
                if(emplPerson.getSalary() < depts.getAvgSalary()) {
                    Iterator<Departments> deptIterInner = departments.iterator();
                    while (deptIterInner.hasNext()) {
                        Departments deptsInner = deptIterInner.next();
                        if(!depts.getDptName().equals(deptsInner.getDptName())) {
                            if(emplPerson.getSalary() > deptsInner.getAvgSalary()) {
                                tmpArray.add(emplPerson.getId() + ";"
                                        + emplPerson.getSalary() + ";"
                                        + deptsInner.getDptName() + ";"
                                        + depts.getDptName() + ";"
                                        + deptsInner.computeTransactionAvgSalary(emplPerson.getSalary()) + ";"
                                        + depts.computeTransactionAvgSalary(-emplPerson.getSalary()));
                            }
                        }
                    }
                }
            }
        }
        //Если были найдены сотрудники, записываем полученный массив данных в выходной файл
        if(tmpArray.size() > 0) {
           try {
               tmpArray.add(0, "//id;name;new department; previous department; " +
                       "new department avg salary; previous department avg  salary");
               Files.write(Paths.get(outputFile), tmpArray, Charset.defaultCharset());
               System.out.println("Данные успешно записаны в файл.");
           }
           catch (IOException e) {
               System.out.println("Ошибка в ходе записи в файл.");
               exit(-1);
           }
           catch (Exception e) {
               System.out.println("Ошибка в ходе обработки записи данных в файл.");
               exit(-1);
           }
        } else {
            System.out.println("Нет сотрудников удовлетворяющих условиям");
        }
    }
}
