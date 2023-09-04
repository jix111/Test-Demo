package server.dao;

import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static config.Static.*;

public class UserDaoImpl implements UserDao {
    @Override
    public int register(String userId, String userPassword) throws SQLException {
        String tableUserName = TABLE_PREFIX + TABLE_USER_NAME;
        Connection connection = DBConnection.getConnection();
        String selectSql = "select * from " + tableUserName + " where userId=?";
        PreparedStatement selectStatement;
        selectStatement = connection.prepareStatement(selectSql);
        selectStatement.setString(1, userId);
        ResultSet resultSet = selectStatement.executeQuery();

        if(resultSet.next()) {
            // 账号已存在
            return 1;
        }

        String insertSql = "insert into " + tableUserName + " values (?,?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, userId);
        insertStatement.setString(2, userPassword);
        int result = insertStatement.executeUpdate();
        if(result != 1) return 2;

        selectStatement.close();
        insertStatement.close();
        resultSet.close();
        return 0;
    }

    @Override
    public int login(String userId, String userPassword) throws SQLException {
        String tableUserName = TABLE_PREFIX + TABLE_USER_NAME;
        Connection connection = DBConnection.getConnection();
        String sql = "select * from " + tableUserName + " where userId=?";
        PreparedStatement statement;
        statement = connection.prepareStatement(sql);
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            String password = resultSet.getString("userPassword");
            if (password.equals(userPassword)) return 0;
            else return 1;
        }
        statement.close();
        resultSet.close();

        // 用户不存在
        return 2;
    }
}
