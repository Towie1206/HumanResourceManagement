
package humanresourcemanagement.model;
import humanresourcemanagement.model.Person;

public class Employee extends Person {
    private int departmentId;
    private String position;
    private int salary;

    public Employee() {
        super();
    }

    public Employee(int employeeId, String name, int departmentId, String position, int salary) {
        super(employeeId, name);
        this.departmentId = departmentId;
        this.position = position;
        this.salary = salary;
    }

    public Employee(String name, int departmentId, String position, int salary) {
        super();
        this.setName(name);
        this.departmentId = departmentId;
        this.position = position;
        this.salary = salary;
    }

    public int getDepartmentId() {

        return departmentId;
    }

    public void setDepartmentId(int departmentId) {

        this.departmentId = departmentId;
    }

    public String getPosition() {

        return position;
    }

    public void setPosition(String position) {

        this.position = position;
    }

    public int getSalary() {

        return salary;
    }

    public void setSalary(int salary) {

        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", departmentId=" + departmentId +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }
}
