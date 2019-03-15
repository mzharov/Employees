import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для хранения данных о департаментах
 */
class Departments {
    private String dptName;
    private BigDecimal avgSalary;
    private List<EmplPerson> emplPersons = new LinkedList<>();

    Departments(String dptName) {
        this.dptName = dptName;
        avgSalary = new BigDecimal(0.0);
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
        avgSalary = new BigDecimal(0.0);
        avgSalary = emplPersons.stream()
                .map(EmplPerson::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        avgSalary = avgSalary.divide(new BigDecimal(emplPersons.size()));
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
    BigDecimal computeTransactionAvgSalary(BigDecimal newEmplSalary) {
        BigDecimal tAvgSalary = emplPersons.stream()
                .map(EmplPerson::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        tAvgSalary = tAvgSalary.add(newEmplSalary);

        if(newEmplSalary.compareTo(new BigDecimal(0)) > 0) {
            tAvgSalary = tAvgSalary.divide(new BigDecimal(emplPersons.size()+1),
                    RoundingMode.HALF_EVEN);
        } else {
            tAvgSalary = tAvgSalary.divide(new BigDecimal(emplPersons.size()-1),
                    RoundingMode.HALF_EVEN);
        }
        return tAvgSalary;
    }

    public String getDptName() {return dptName;}
    public BigDecimal getAvgSalary() {return avgSalary;}
    public List<EmplPerson> getEmployeersList() {return emplPersons;}

}