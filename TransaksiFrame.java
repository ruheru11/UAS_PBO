
package UAS_PBO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;

public class TransaksiFrame extends JFrame {
    private JComboBox<String> konsumenDropdown, barangDropdown;
    private JTextField quantityField, totalBiayaField;
    private JTable table;
    private DefaultTableModel tableModel;
    private TransaksiService transaksiService;

    public TransaksiFrame() {
        transaksiService = new TransaksiService();

        setTitle("CRUD Data Transaksi");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Konsumen:"));
        konsumenDropdown = new JComboBox<>();
        loadKonsumenDropdown();
        inputPanel.add(konsumenDropdown);

        inputPanel.add(new JLabel("Barang:"));
        barangDropdown = new JComboBox<>();
        loadBarangDropdown();
        inputPanel.add(barangDropdown);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Total Biaya:"));
        totalBiayaField = new JTextField();
        totalBiayaField.setEditable(false);
        inputPanel.add(totalBiayaField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this::handleCalculate);
        inputPanel.add(calculateButton);

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
        tableModel = new DefaultTableModel(new String[]{"ID Transaksi", "Konsumen", "Barang", "Quantity", "Total Biaya"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();
    }

    private void loadKonsumenDropdown() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id_konsumen, nama_konsumen FROM data_konsumen";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                konsumenDropdown.addItem(rs.getInt("id_konsumen") + " - " + rs.getString("nama_konsumen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBarangDropdown() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id_barang, nama_barang FROM data_barang";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                barangDropdown.addItem(rs.getInt("id_barang") + " - " + rs.getString("nama_barang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleCalculate(ActionEvent e) {
        String selectedBarang = (String) barangDropdown.getSelectedItem();
        if (selectedBarang != null) {
            int idBarang = Integer.parseInt(selectedBarang.split(" - ")[0]);
            int quantity = Integer.parseInt(quantityField.getText());
            int hargaBarang = transaksiService.getHargaBarang(idBarang);
            int totalBiaya = quantity * hargaBarang;
            totalBiayaField.setText(String.valueOf(totalBiaya));
        }
    }

    private void handleCreate(ActionEvent e) {
        String selectedKonsumen = (String) konsumenDropdown.getSelectedItem();
        String selectedBarang = (String) barangDropdown.getSelectedItem();

        if (selectedKonsumen != null && selectedBarang != null) {
            int idKonsumen = Integer.parseInt(selectedKonsumen.split(" - ")[0]);
            int idBarang = Integer.parseInt(selectedBarang.split(" - ")[0]);
            int quantity = Integer.parseInt(quantityField.getText());
            int totalBiaya = Integer.parseInt(totalBiayaField.getText());
            transaksiService.createTransaksi(idKonsumen, idBarang, quantity, totalBiaya);
            refreshTable();
            clearFields();
        }
    }

    private void handleUpdate(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int idTransaksi = (int) tableModel.getValueAt(selectedRow, 0);
            String selectedKonsumen = (String) konsumenDropdown.getSelectedItem();
            String selectedBarang = (String) barangDropdown.getSelectedItem();
            if (selectedKonsumen != null && selectedBarang != null) {
                int idKonsumen = Integer.parseInt(selectedKonsumen.split(" - ")[0]);
                int idBarang = Integer.parseInt(selectedBarang.split(" - ")[0]);
                int quantity = Integer.parseInt(quantityField.getText());
                int totalBiaya = Integer.parseInt(totalBiayaField.getText());
                transaksiService.updateTransaksi(idTransaksi, idKonsumen, idBarang, quantity, totalBiaya);
                refreshTable();
                clearFields();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate.");
        }
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int idTransaksi = (int) tableModel.getValueAt(selectedRow, 0);
            transaksiService.deleteTransaksi(idTransaksi);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
        }
    }

    private void refreshTable() {
        List<Transaksi> transaksis = transaksiService.readTransaksi();
        tableModel.setRowCount(0);
        for (Transaksi transaksi : transaksis) {
            tableModel.addRow(new Object[]{
                    transaksi.getIdTransaksi(),
                    transaksi.getNamaKonsumen(),
                    transaksi.getNamaBarang(),
                    transaksi.getQuantity(),
                    transaksi.getTotalBiaya()
            });
        }
    }

    private void clearFields() {
        quantityField.setText("");
        totalBiayaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TransaksiFrame().setVisible(true));
    }
}

