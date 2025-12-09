package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false&allowPublicKeyRetrieval=true";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT id, name, email, country FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USERS_SQL = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?";

    public UserDAO() {}

    // Kết nối MySQL
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Sửa driver mới
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    // ============================= INSERT =============================
    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USERS_SQL)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    // ============================= SELECT BY ID =============================
    @Override
    public User selectUser(int id) {
        User user = null;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(
                        id,
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                );
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return user;
    }

    // ============================= SELECT ALL =============================
    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                ));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return users;
    }

    // ============================= DELETE =============================
    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean deleted;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USERS_SQL)) {

            ps.setInt(1, id);
            deleted = ps.executeUpdate() > 0;

        }
        return deleted;
    }

    // ============================= UPDATE =============================
    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean updated;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USERS_SQL)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getCountry());
            ps.setInt(4, user.getId());

            updated = ps.executeUpdate() > 0;
        }

        return updated;
    }

    // ======================= STORED PROCEDURE: SELECT BY ID =======================
    @Override
    public User getUserById(int id) {
        User user = null;

        String query = "{CALL get_user_by_id(?)}";

        try (Connection connection = getConnection();
             CallableStatement cs = connection.prepareCall(query)) {

            cs.setInt(1, id);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                user = new User(
                        id,
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                );
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return user;
    }

    // ======================= STORED PROCEDURE: INSERT =======================
    @Override
    public void insertUserStore(User user) throws SQLException {
        String query = "{CALL insert_user(?,?,?)}";

        try (Connection connection = getConnection();
             CallableStatement cs = connection.prepareCall(query)) {

            cs.setString(1, user.getName());
            cs.setString(2, user.getEmail());
            cs.setString(3, user.getCountry());

            cs.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    // ======================= STORED PROCEDURE: SELECT ALL USERS =======================
    public List<User> getAllUsersSP() {
        List<User> users = new ArrayList<>();

        String query = "{CALL get_all_users()}";

        try (Connection connection = getConnection();
             CallableStatement cs = connection.prepareCall(query)) {

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                ));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return users;
    }

    // ============================= TRANSACTION =============================
    @Override
    public void addUserTransaction(User user, List<Integer> permissions) {
        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psAssign = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            psInsert = conn.prepareStatement(INSERT_USERS_SQL, Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, user.getName());
            psInsert.setString(2, user.getEmail());
            psInsert.setString(3, user.getCountry());
            int rowAffected = psInsert.executeUpdate();

            rs = psInsert.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) userId = rs.getInt(1);

            if (rowAffected == 1) {
                String sqlAssign = "INSERT INTO user_permision (user_id, permision_id) VALUES (?, ?)";
                psAssign = conn.prepareStatement(sqlAssign);

                for (int perm : permissions) {
                    psAssign.setInt(1, userId);
                    psAssign.setInt(2, perm);
                    psAssign.executeUpdate();
                }

                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psInsert != null) psInsert.close();
                if (psAssign != null) psAssign.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ============================= EXCEPTION HANDLER =============================
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
            }
        }
    }
}