package dao;

import model.HocSinh;
import java.sql.*;
import java.util.*;

public class HocSinhDAO {

    public List<HocSinh> findAll() {
        List<HocSinh> list = new ArrayList<>();
        String sql = "SELECT * FROM HocSinh";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new HocSinh(
                        rs.getInt("maHocSinh"),
                        rs.getString("hoTen"),
                        rs.getString("lop")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}