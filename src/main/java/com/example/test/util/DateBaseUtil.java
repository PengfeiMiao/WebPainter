package com.example.test.util;

import java.sql.*;

/**
 * @author: xy
 * @create: 2019-08-22
 */
public class DateBaseUtil {

    /**
     * 获取链接
     **/
    public static Connection getConnection(String driveClassName, String url, String userName, String password) {
        Connection connection = null;
        //1.加载驱动程序
        try {
            Class.forName(driveClassName);
            //2. 获得数据库连接
            connection = DriverManager.getConnection(url, userName, password
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 回滚事务
     **/
    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭
     **/
    public static void close(Connection conn, PreparedStatement stmt, ResultSet resultSet) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
