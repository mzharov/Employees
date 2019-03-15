/**
 * Хранение данных о сотрудника
 */
class EmplPerson {

    private String lastName; //Фамилия
    private double salary; //зарплата
    private int id; //Идентификатор

    EmplPerson(int id, String lastName, double salary) {
        this.id = id;
        this.lastName = lastName;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return lastName + ";"  + salary;
    }

    public double getSalary() {return salary;}
    public String getLastName() {return lastName;}
    public int getId() {return id;}


}