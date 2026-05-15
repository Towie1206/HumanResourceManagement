package humanresourcemanagement.frame;

import humanresourcemanagement.AB.ContractAB;
import humanresourcemanagement.model.Contract;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ContractFrame extends JFrame {
    private ContractAB contractAB = new ContractAB();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtEmpId, txtType, txtDate;
    private JCheckBox chkInsurance;

    public ContractFrame() {
        setTitle("Quản lý Hợp đồng");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("ID Hợp đồng (Sửa/Xóa):")); txtId = new JTextField(); inputPanel.add(txtId);
        inputPanel.add(new JLabel("Mã Nhân viên:")); txtEmpId = new JTextField(); inputPanel.add(txtEmpId);
        inputPanel.add(new JLabel("Loại hợp đồng:")); txtType = new JTextField(); inputPanel.add(txtType);
        inputPanel.add(new JLabel("Ngày ký (YYYY-MM-DD):")); txtDate = new JTextField(); inputPanel.add(txtDate);
        inputPanel.add(new JLabel("Bảo hiểm:")); chkInsurance = new JCheckBox("Có bảo hiểm"); inputPanel.add(chkInsurance);
        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Loại", "Ngày ký", "Bảo hiểm"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm HĐ");
        JButton btnDelete = new JButton("Xóa HĐ");
        JButton btnReload = new JButton("Làm mới");
        btnPanel.add(btnAdd); btnPanel.add(btnDelete); btnPanel.add(btnReload);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            try {
                Contract c = new Contract(0, Integer.parseInt(txtEmpId.getText()), txtType.getText(),
                        Date.valueOf(txtDate.getText()), chkInsurance.isSelected());
                contractAB.addContract(c);
                loadData();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        });

        btnReload.addActionListener(e -> loadData());
        loadData();
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Contract> list = contractAB.getAllContracts();
            for (Contract c : list) tableModel.addRow(new Object[]{c.getId(), c.getEmployeeID(), c.getContractType(), c.getSigningDate(), c.hasInsurance() ? "Có" : "Không"});
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}