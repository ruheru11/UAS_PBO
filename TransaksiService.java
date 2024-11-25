package UAS_PBO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiService {

    public void createTransaksi(int idKonsumen, int idBarang, int quantity, int totalBiaya) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO data_transaksi (id_konsumen, id_barang, quantity, total_biaya) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idKonsumen);
            pstmt.setInt(2, idBarang);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, totalBiaya);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTransaksi(int idTransaksi, int idKonsumen, int idBarang, int quantity, int totalBiaya) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE data_transaksi SET id_konsumen = ?, id_barang = ?, quantity = ?, total_biaya = ? WHERE id_transaksi = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idKonsumen);
            pstmt.setInt(2, idBarang);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, totalBiaya);
            pstmt.setInt(5, idTransaksi);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaksi(int idTransaksi) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM data_transaksi WHERE id_transaksi = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idTransaksi);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaksi> readTransaksi() {
        List<Transaksi> transaksiList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT t.id_transaksi, k.nama_konsumen, b.nama_barang, t.quantity, t.total_biaya " +
                           "FROM data_transaksi t " +
                           "JOIN data_konsumen k ON t.id_konsumen = k.id_konsumen " +
                           "JOIN data_barang b ON t.id_barang = b.id_barang";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int idTransaksi = rs.getInt("id_transaksi");
                String namaKonsumen = rs.getString("nama_konsumen");
                String namaBarang = rs.getString("nama_barang");
                int quantity = rs.getInt("quantity");
                int totalBiaya = rs.getInt("total_biaya");

                Transaksi transaksi = new Transaksi(idTransaksi, namaKonsumen, namaBarang, quantity, totalBiaya);
                transaksiList.add(transaksi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaksiList;
    }

    public int getHargaBarang(int idBarang) {
        int harga = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT harga_barang FROM data_barang WHERE id_barang = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idBarang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                harga = rs.getInt("harga_barang");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return harga;
    }
}

