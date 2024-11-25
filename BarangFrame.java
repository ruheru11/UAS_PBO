package UAS_PBO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangFrame extends JFrame {
    private JTextField namaField, hargaField;
    private JTable table;
    private DefaultTableModel tableModel;
    private BarangService barangService;

    public BarangFrame() {
        barangService = new BarangService();

        setTitle("CRUD Data Barang");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nama Barang:"));
        namaField = new JTextField();
        inputPanel.add(namaField);

        inputPanel.add(new JLabel("Harga Barang:"));
        hargaField = new JTextField();
        inputPanel.add(hargaField);

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
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Barang", "Harga"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();
    }

    private void handleCreate(ActionEvent e) {
        String nama = namaField.getText();
        double harga = Double.parseDouble(hargaField.getText());
        barangService.createBarang(nama, harga);
        refreshTable();
        clearFields();
    }

    private void handleUpdate(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            barangService.updateBarang(id, nama, harga);
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
            barangService.deleteBarang(id);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
        }
    }

    private void refreshTable() {
        List<Barang> barangs = barangService.readBarang();
        tableModel.setRowCount(0);
        for (Barang barang : barangs) {
            tableModel.addRow(new Object[]{barang.getId(), barang.getNama(), barang.getHarga()});
        }
    }

    private void clearFields() {
        namaField.setText("");
        hargaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BarangFrame().setVisible(true));
    }
}
