package restaurantsystem.model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseManager {
    private static HikariDataSource dataSource;

    // Configure the connection pool
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/Restaurent_Manager");
        config.setUsername("manager");
        config.setPassword("DBMSproject");
        config.setMaximumPoolSize(10);  // Maximum connections in the pool
        dataSource = new HikariDataSource(config);
    }

    // Method to get a connection from the pool
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Example function to fetch user role
    public static String getUserRole(String username, String password) {
        String role = null;
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT u_role FROM users WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("u_role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public static String getUserName(String username, String password) {
        String name = null;
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT u_name FROM users WHERE username = ? AND u_role = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("u_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static int getItemId(String name) {
        int id=-1;
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT idItem FROM item WHERE name = ? ")) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idItem");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static Item getItemByName(String name) {
        Item item = null;
        String query = "SELECT * FROM item WHERE name = ?";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                item = new Item();
                item.setId(rs.getInt("idItem"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


}
