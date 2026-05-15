
package humanresourcemanagement.model;
import java.sql.Date;
import java.time.LocalDate;
public class Contract {
    private int id;
    private int employeeID;
    private String contractType;
    private Date signingDate;
    private boolean insurance;
    public Contract() {
    }

    public Contract(int id, int employeeID, String contractType, Date signingDate, boolean insurance) {
        this.id = id;
        this.employeeID = employeeID;
        this.contractType = contractType;
        this.signingDate = signingDate;
        this.insurance = insurance;
    }
    public Contract(int employeeID, String contractType, Date signingDate, boolean insurance) {
        this.employeeID = employeeID;
        this.contractType = contractType;
        this.signingDate = signingDate;
        this.insurance = insurance;
    }
    public Contract(int employeeID, String contractType, LocalDate signingDate, boolean insurance) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public String getContractType() {
        return contractType;
    }
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
    public Date getSigningDate() {
        return signingDate;
    }
    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }
    public boolean hasInsurance() {
        return insurance;
    }
    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }
    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", employeeID=" + employeeID +
                ", contractType='" + contractType + '\'' +
                ", signingDate=" + signingDate +
                ", insurance=" + insurance +
                '}';
    }
}