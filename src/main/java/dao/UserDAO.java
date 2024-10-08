package dao;

import db.Myconnection;
import model.User;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = Myconnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT email FORM USER");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String e = rs.getString(1);
            if (e.equals(email)) return true;
        }
        return false;
    }

    public static int saveUser(User user) throws SQLException {
        Connection connection = Myconnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO user VALUES (default, ?,?)");
        ps.setString(1,user.getName());
        ps.setString(2, user.getEmail());
        return ps.executeUpdate();

    }
}
