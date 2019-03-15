import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для хранения данных о департаментах
 */
class Departments {
    private String dptName;
    private BigDecimal allSalaries;
    private List<EmplPerson> emplPersons = new LinkedList<>();

    public Departments(String dptName) {
        this.dptName = dptName;
        allSalaries = new BigDecimal(0);
    }

    /**
     * Добавление нового сотрудика
     * @param empl объект нового сотрудника
     */
    void addEmpl(EmplPerson empl) {
        emplPersons.add(empl);
        allSalaries = allSalaries.add(empl.getSalary());
    }
    /**
     * Вычисление средней зарплаты по департаменты
     */
    private BigDecimal computeAvgSalary() {
        return allSalaries.divide(new BigDecimal(emplPersons.size()), RoundingMode.UP);
    }
    @Override
    public String toString() {
        return dptName + " " + getAvgSalary();
    }

    /**
     * Вычисление возможной средней зарплаты по департаменту при переводе сотрудника
     * @param newEmplSalary зарплата сотрудника; при добавлении передается параметр  >0, при удалении  <0
     * @return средне значение зарплаты по департаменту
     */

    private BigDecimal computeTransactionAvgSalary(BigDecimal newEmplSalary) {
        BigDecimal tAvgSalary = allSalaries;
        tAvgSalary = tAvgSalary.add(newEmplSalary);

        if(newEmplSalary.compareTo(new BigDecimal(0)) > 0) {
            tAvgSalary = tAvgSalary.divide(new BigDecimal(emplPersons.size()+1),
                    RoundingMode.UP);
        } else {
            tAvgSalary = tAvgSalary.divide(new BigDecimal(emplPersons.size()-1),
                    RoundingMode.UP);
        }
        return tAvgSalary;
    }

    public String getDptName() {return dptName;}
    public BigDecimal getAvgSalary() {return computeAvgSalary();}
    public List<EmplPerson> getEmployeersList() {return emplPersons;}
    public BigDecimal getTAvgSalary(BigDecimal newEmplSalary) {
        return computeTransactionAvgSalary(newEmplSalary);
    }

}
