package UAS_PBO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KonsumenService {

    public void createKonsumen(String nama) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO data_konsumen (nama_konsumen) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nama);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Konsumen> readKonsumen() {
        List<Konsumen> konsumens = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM data_konsumen";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                konsumens.add(new Konsumen(rs.getInt("id_konsumen"), rs.getString("nama_konsumen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return konsumens;
    }

    public void updateKonsumen(int id, String nama) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE data_konsumen SET nama_konsumen = ? WHERE id_konsumen = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nama);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteKonsumen(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM data_konsumen WHERE id_konsumen = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

