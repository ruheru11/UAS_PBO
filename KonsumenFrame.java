package UAS_PBO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KonsumenFrame extends JFrame {
    private JTextField namaField;
    private JTable table;
    private DefaultTableModel tableModel;
    private KonsumenService konsumenService;

    public KonsumenFrame() {
        konsumenService = new KonsumenService();

        setTitle("CRUD Data Konsumen");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nama Konsumen:"));
        namaField = new JTextField();
        inputPanel.add(namaField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(this::handleCreate);
        inputPanel.add(createButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(this::handleUpdate);
        inputPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::handleDelete);
        inputPanel.add(deleteButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        inputPanel.add(refreshButton);

        add(inputPanel, BorderLayout.NORTH);

        // Tabel
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Konsumen"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();
    }

    private void handleCreate(ActionEvent e) {
        String nama = namaField.getText();
        konsumenService.createKonsumen(nama);
        refreshTable();
        clearFields();
    }

    private void handleUpdate(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nama = namaField.getText();
            konsumenService.updateKonsumen(id, nama);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate.");
        }
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            konsumenService.deleteKonsumen(id);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
        }
    }

    private void refreshTable() {
        List<Konsumen> konsumens = konsumenService.readKonsumen();
        tableModel.setRowCount(0);
        for (Konsumen konsumen : konsumens) {
            tableModel.addRow(new Object[]{konsumen.getId(), konsumen.getNama()});
        }
    }

    private void clearFields() {
        namaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KonsumenFrame().setVisible(true));
    }
}

