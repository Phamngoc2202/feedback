package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends DBContext {
    
    // Hàm đăng nhập (Đã có từ trước)
    public User checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), 
                                rs.getString("password_hash"), rs.getString("full_name"), 
                                rs.getString("email"), rs.getInt("role_id"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi đăng nhập: " + e.getMessage());
        }
        return null;
    }

    // 1. Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt("user_id"), rs.getString("username"), 
                                  rs.getString("password_hash"), rs.getString("full_name"), 
                                  rs.getString("email"), rs.getInt("role_id")));
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách User: " + e.getMessage());
        }
        return list;
    }

    // 2. Thêm người dùng mới
    public boolean insertUser(User u) {
        String query = "INSERT INTO users (username, password_hash, full_name, email, role_id) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getRoleId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi thêm User: " + e.getMessage());
            return false;
        }
    }

    // 3. Xóa người dùng theo ID
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi xóa User (Có thể do dính khóa ngoại): " + e.getMessage());
            return false;
        }
    }
    
    // Lấy 1 User theo ID để hiển thị lên form Edit
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), 
                                rs.getString("password_hash"), rs.getString("full_name"), 
                                rs.getString("email"), rs.getInt("role_id"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi getUserById: " + e.getMessage());
        }
        return null;
    }

    // Cập nhật thông tin User
    public boolean updateUser(User u) {
        String query = "UPDATE users SET username=?, password_hash=?, full_name=?, email=?, role_id=? WHERE user_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getRoleId());
            ps.setInt(6, u.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi updateUser: " + e.getMessage());
            return false;
        }
    }
}