package humanresourcemanagement.frame;

import humanresourcemanagement.AB.TimekeepingAB;
import humanresourcemanagement.model.Timekeeping;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TimekeepingFrame extends JFrame {
    private TimekeepingAB tkAB = new TimekeepingAB();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtEmpId, txtDate, txtHours;

    public TimekeepingFrame() {
        setTitle("Quản lý Chấm công");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin chấm công"));

        inputPanel.add(new JLabel("ID (Sửa/Xóa):")); txtId = new JTextField(); inputPanel.add(txtId);
        inputPanel.add(new JLabel("Mã Nhân viên:")); txtEmpId = new JTextField(); inputPanel.add(txtEmpId);
        inputPanel.add(new JLabel("Ngày (YYYY-MM-DD):")); txtDate = new JTextField(); inputPanel.add(txtDate);
        inputPanel.add(new JLabel("Số giờ làm:")); txtHours = new JTextField(); inputPanel.add(txtHours);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Ngày", "Giờ làm"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Chấm công");
        JButton btnDelete = new JButton("Xóa");
        JButton btnReload = new JButton("Làm mới");
        btnPanel.add(btnAdd); btnPanel.add(btnDelete); btnPanel.add(btnReload);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            try {
                Timekeeping tk = new Timekeeping(0, Integer.parseInt(txtEmpId.getText()),
                        Date.valueOf(txtDate.getText()), Integer.parseInt(txtHours.getText()));
                tkAB.add(tk);
                JOptionPane.showMessageDialog(this, "Chấm công thành công!");
                loadData();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        });

        btnDelete.addActionListener(e -> {
            try {
                tkAB.delete(Integer.parseInt(txtId.getText()));
                loadData();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        });

        btnReload.addActionListener(e -> loadData());
        loadData();
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Timekeeping> list = tkAB.getAll();
            for (Timekeeping t : list) tableModel.addRow(new Object[]{t.getId(), t.getEmployeeID(), t.getWorkDate(), t.getHoursWorked()});
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}