package dao;

import dal.DBContext; // Thêm dòng này để gọi DBContext từ thư mục dal
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class UserDAO extends DBContext {
    
    public User checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        try {
            // Sử dụng biến connection từ DBContext
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getInt("role_id")
                );
            }
        } catch (Exception e) {
            System.out.println("Lỗi đăng nhập: " + e.getMessage());
        }
        return null;
    }
}