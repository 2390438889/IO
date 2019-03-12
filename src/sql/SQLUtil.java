package sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hearts
 * @date 2019/3/11
 * @desc
 */
public class SQLUtil {

    private static final String  URL = "jdbc:mysql://localhost:3306/test";

    private static final String USERNAME= "root";

    private static final String PASSWORD = "root";

    private static Connection conn;

    static{
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String,String>> queryAll(final String tableName){

        List<Map<String,String>> maps = new ArrayList<Map<String, String>>();

        //构建sql
        String sql = "select * from "+tableName;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //获得所有的列名
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                Map<String,String> map = new HashMap<String, String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String colName = rsmd.getColumnName(i);
                    String value = rs.getString(i);
                    map.put(colName,value);
                }
                maps.add(map);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maps;

    }
}
