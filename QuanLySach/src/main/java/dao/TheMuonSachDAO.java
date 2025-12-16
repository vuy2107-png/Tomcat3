package dao;

import model.TheMuonSach;
// Cần thêm các imports sau:
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Hoặc sử dụng Wildcard Imports (như bạn đã dùng ở đầu)

public class TheMuonSachDAO {

    public List<TheMuonSach> findAll() {
        List<TheMuonSach> list = new ArrayList<>();
        String sql = "SELECT * FROM TheMuonSach";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TheMuonSach t = new TheMuonSach();
                t.setMaMuonSach(rs.getInt("maMuonSach"));
                t.setMaSach(rs.getInt("maSach"));
                t.setMaHocSinh(rs.getInt("maHocSinh"));
                t.setTrangThai(rs.getBoolean("trangThai"));
                t.setNgayMuon(rs.getDate("ngayMuon"));
                t.setNgayTra(rs.getDate("ngayTra"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Dùng cho Transaction (Mượn sách)
    public void insert(TheMuonSach t, Connection conn) throws SQLException {
        String sql = "INSERT INTO TheMuonSach VALUES (NULL,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getMaSach());
            ps.setInt(2, t.getMaHocSinh());
            ps.setBoolean(3, t.isTrangThai());
            ps.setDate(4, t.getNgayMuon());
            ps.setDate(5, t.getNgayTra());
            ps.executeUpdate();
        }
    }

    // Dùng cho Màn hình Thống kê (Yêu cầu 3.1)
    public List<Map<String, Object>> findBorrowed(String keyword) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT t.*, s.tenSach, s.tacGia, hs.hoTen, hs.lop " +
                "FROM TheMuonSach t " +
                "JOIN Sach s ON t.maSach = s.maSach " +
                "JOIN HocSinh hs ON t.maHocSinh = hs.maHocSinh " +
                "WHERE t.trangThai = TRUE ";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += "AND (s.tenSach LIKE ? OR hs.hoTen LIKE ?) ";
        }

        sql += "ORDER BY t.maMuonSach DESC";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                String search = "%" + keyword + "%";
                ps.setString(1, search);
                ps.setString(2, search);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("maMuonSach", "MS-" + rs.getInt("maMuonSach"));
                    map.put("maMuonSachInt", rs.getInt("maMuonSach"));
                    map.put("tenSach", rs.getString("tenSach"));
                    map.put("tacGia", rs.getString("tacGia"));
                    map.put("hoTen", rs.getString("hoTen"));
                    map.put("lop", rs.getString("lop"));
                    map.put("ngayMuon", rs.getDate("ngayMuon"));
                    map.put("ngayTra", rs.getDate("ngayTra"));
                    list.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Dùng cho Transaction (Trả sách)
    public int traSach(int maMuonSach, Connection conn) throws SQLException {
        String sqlSelect = "SELECT maSach FROM TheMuonSach WHERE maMuonSach = ? AND trangThai = TRUE";
        int maSach = -1;

        // 1. Lấy mã sách
        try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {
            psSelect.setInt(1, maMuonSach);
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    maSach = rs.getInt("maSach");
                }
            }
        }

        // 2. Cập nhật trạng thái thẻ mượn
        if (maSach != -1) {
            String sqlUpdate = "UPDATE TheMuonSach SET trangThai = FALSE, ngayTra = ? WHERE maMuonSach = ?";
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setDate(1, new Date(System.currentTimeMillis()));
                psUpdate.setInt(2, maMuonSach);
                psUpdate.executeUpdate();
            }
        }
        return maSach;
    }
}