import java.math.BigDecimal;

/**
 * Хранение данных о сотрудника
 */
class EmplPerson {

    private String lastName; //Фамилия
    private BigDecimal salary; //зарплата
    private int id; //Идентификатор

    public EmplPerson(int id, String lastName, BigDecimal salary) {
        this.id = id;
        this.lastName = InputLineFormatter.formateStringToCase(lastName);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return lastName + ";"  + salary;
    }

    public BigDecimal getSalary() {return salary;}
    public String getLastName() {return lastName;}
    public int getId() {return id;}

}