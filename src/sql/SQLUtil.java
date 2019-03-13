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

    private static final String  URL = "jdbc:mysql://localhost:3306/";

    private static final String USERNAME= "root";

    private static final String PASSWORD = "root";

    private static final String DATABASE_NAME = "test";

    private static Connection conn;

    static{
        connect();
    }

    public static boolean connect(String dataBaseName){
        return connect(URL,USERNAME,PASSWORD,dataBaseName);
    }

    public static boolean connect(){
        return connect(DATABASE_NAME);
    }

    public static boolean connect(String url,String username,String password,String dataBaseName){
        try {
            conn = DriverManager.getConnection(url+dataBaseName,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn != null;
    }

    public static List<Map<String,String>> query(final String sql) {

        System.out.println(sql);

        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();

        if (conn != null && sql!=null && sql.trim().length()!=0) {

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                //获得所有的列名
                ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    Map<String, String> map = new HashMap<String, String>();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        String colName = rsmd.getColumnName(i);
                        String value = rs.getString(i);
                        map.put(colName, value);
                    }
                    maps.add(map);

                }
            } catch (SQLException e) {
                System.out.println(sql);
                e.printStackTrace();
            }

        }

        return maps;

    }

    public static List<Map<String,String>> queryByCondition(QueryCondition queryCondition){
        return query(queryCondition.createSQL());
    }

    public static List<Map<String,String>> queryByTable(String tableName){

        return queryByCondition(new QueryCondition().getBuilder().setTableName("book").build());
    }
}
