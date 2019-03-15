import java.util.LinkedList;
import java.util.List;

/**
 * Класс для хранения данных о департаментах
 */
class Departments {
    private String dptName;
    private Double avgSalary;
    private List<EmplPerson> emplPersons = new LinkedList<>();

    Departments(String dptName) {
        this.dptName = dptName;
        avgSalary = 0.0;
    }

    /**
     * Добавление нового сотрудика
     * @param empl объект нового сотрудника
     */
    void addEmpl(EmplPerson empl) {
        emplPersons.add(empl);
    }
    /**
     * Вычисление средней зарплаты по департаменты
     */
    void computeAvgSalary() {
        avgSalary = 0.0;
        emplPersons.forEach((n-> avgSalary+=n.getSalary()));
        avgSalary /= emplPersons.size();
    }
    @Override
    public String toString() {
        return dptName + " " + avgSalary;
    }

    /**
     * Вычисление возможной средней зарплаты по департаменту при переводе сотрудника
     * @param newEmplSalary зарплата сотрудника; при добавлении передается параметр  >0, при удалении  <0
     * @return средне значение зарплаты по департаменту
     */
    double computeTransactionAvgSalary(double newEmplSalary) {
        double tAvgSalary = 0.0;
        tAvgSalary = emplPersons.stream().mapToDouble(n->n.getSalary()).sum();
        tAvgSalary +=newEmplSalary;
        if(newEmplSalary > 0) {
            tAvgSalary /= emplPersons.size() + 1;
        } else {
            tAvgSalary /= emplPersons.size() - 1;
        }
        return tAvgSalary;
    }

    public String getDptName() {return dptName;}
    public double getAvgSalary() {return avgSalary;}
    public List<EmplPerson> getEmployeersList() {return emplPersons;}

}