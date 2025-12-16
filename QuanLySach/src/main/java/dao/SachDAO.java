package dao;

import model.Sach;
import java.sql.*;
import java.util.*;

public class SachDAO {

    public List<Sach> findAll() {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM Sach";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Sach(
                        rs.getInt("maSach"),
                        rs.getString("tenSach"),
                        rs.getString("tacGia"),
                        rs.getString("moTa"),
                        rs.getInt("soLuong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Sach findById(int maSach) {
        String sql = "SELECT * FROM Sach WHERE maSach = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sach(
                            rs.getInt("maSach"),
                            rs.getString("tenSach"),
                            rs.getString("tacGia"),
                            rs.getString("moTa"),
                            rs.getInt("soLuong")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Sach s) {
        String sql = "INSERT INTO Sach VALUES (NULL,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, s.getTenSach());
            ps.setString(2, s.getTacGia());
            ps.setString(3, s.getMoTa());
            ps.setInt(4, s.getSoLuong());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Dùng cho Transaction (Mượn sách)
    public void giamSoLuong(int maSach, Connection conn) throws SQLException {
        String sql = "UPDATE Sach SET soLuong = soLuong - 1 WHERE maSach=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSach);
            ps.executeUpdate();
        }
    }

    // Dùng cho Transaction (Trả sách)
    public void tangSoLuong(int maSach, Connection conn) throws SQLException {
        String sql = "UPDATE Sach SET soLuong = soLuong + 1 WHERE maSach=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSach);
            ps.executeUpdate();
        }
    }
}