package DBUtill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import Servlet.User;


public class DBUtils {
    private static String driver = "com.mysql.jdbc.Driver";//MySQL 驱动
    private static String url = "jdbc:mysql://10.0.2.2/AndroidServlet";//MYSQL数据库连接Url
    private static String user = "root";//用户名
    private static String password = "123456";//密码
 
    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver); //
            conn = DriverManager.getConnection(url,user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
    public static Map<String, String> login(User user) {
        HashMap<String, String> map = new HashMap<>();
        Connection conn = getConnection();
        try {
            Statement st = conn.createStatement();
            String sql= "select * from servicedata where username ='" + user.getUsername()
                    + "' and pwd ='" + user.getPassword() + "'";
            ResultSet res = st.executeQuery(sql);
            if (res == null) {
                return null;
            } else {
                int cnt = res.getMetaData().getColumnCount();
                res.next();
                for (int i = 1; i <= cnt; ++i) {
                    String field = res.getMetaData().getColumnName(i);
                    map.put(field, res.getString(field));
                }
                conn.close();
                st.close();
                res.close();
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }}