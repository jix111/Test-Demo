package server.dao;

import java.sql.SQLException;

public interface UserDao {
    // 返回 0:成功 1:用户名或密码错误 2:账号不存在
    int login(String userId, String userPassword) throws SQLException;

    // 返回 0:成功 1:账号已存在 2:数据注入错误
    int register(String userId, String userPassword) throws SQLException;
}
