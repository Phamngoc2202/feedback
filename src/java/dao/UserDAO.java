package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends DBContext {

    public User checkLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            System.out.println("Loi dang nhap: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY user_id";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUser(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi getAllUsers: " + e.getMessage());
        }
        return list;
    }

    public List<User> searchUsers(String keyword) {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users "
                + "WHERE username LIKE ? OR full_name LIKE ? OR email LIKE ? "
                + "ORDER BY user_id";
        String pattern = "%" + keyword + "%";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUser(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi searchUsers: " + e.getMessage());
        }
        return list;
    }

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
            System.out.println("Loi insertUser: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi deleteUser: " + e.getMessage());
            return false;
        }
    }

    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            System.out.println("Loi getUserById: " + e.getMessage());
        }
        return null;
    }

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
            System.out.println("Loi updateUser: " + e.getMessage());
            return false;
        }
    }

    public boolean usernameExists(String username, int excludeUserId) {
        String query = "SELECT 1 FROM users WHERE username = ? AND user_id <> ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, excludeUserId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi usernameExists: " + e.getMessage());
        }
        return true;
    }

    private User mapUser(ResultSet rs) throws Exception {
        return new User(rs.getInt("user_id"), rs.getString("username"),
                rs.getString("password_hash"), rs.getString("full_name"),
                rs.getString("email"), rs.getInt("role_id"));
    }
}
