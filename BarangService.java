package UAS_PBO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangService {

    public void createBarang(String nama, double harga) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO data_barang (nama_barang, harga_barang) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nama);
            stmt.setDouble(2, harga);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Barang> readBarang() {
        List<Barang> barangs = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM data_barang";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                barangs.add(new Barang(rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getDouble("harga_barang")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barangs;
    }

    public void updateBarang(int id, String nama, double harga) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE data_barang SET nama_barang = ?, harga_barang = ? WHERE id_barang = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nama);
            stmt.setDouble(2, harga);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBarang(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM data_barang WHERE id_barang = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
