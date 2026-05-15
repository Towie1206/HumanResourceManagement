
package humanresourcemanagement.model;

import humanresourcemanagement.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Timekeeping {
    private int id;
    private int employeeID;
    private Date workDate;
    private int hoursWorked;

    public Timekeeping() {
    }

    public Timekeeping(int id, int employeeID, Date workDate, int hoursWorked) {
        this.id = id;
        this.employeeID = employeeID;
        this.workDate = workDate;
        this.hoursWorked = hoursWorked;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getEmployeeID() {

        return employeeID;
    }

    public void setEmployeeID(int employeeID) {

        this.employeeID = employeeID;
    }

    public Date getWorkDate() {

        return workDate;
    }

    public void setWorkDate(Date workDate) {

        this.workDate = workDate;
    }

    public int getHoursWorked() {

        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {

        this.hoursWorked = hoursWorked;
    }

    public static List<Timekeeping> getAllTimekeepings() {
        List<Timekeeping> timekeepingList = new ArrayList<>();
        String sql = "SELECT * FROM Timekeeping";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Timekeeping tk = new Timekeeping(
                        rs.getInt("id"),
                        rs.getInt("employeeID"),
                        rs.getDate("workDate"),
                        rs.getInt("hoursWorked")
                );
                timekeepingList.add(tk);
            }
        } catch (SQLException e) {
            System.err.println("Error get timekeeping list : " + e.getMessage());
        }
        return timekeepingList;
    }

    @Override
    public String toString() {
        return "Timekeeping{" +
                "id=" + id +
                ", employeeID=" + employeeID +
                ", workDate=" + workDate +
                ", hoursWorked=" + hoursWorked +
                '}';
    }
}
