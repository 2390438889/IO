package util;

import sql.QueryCondition;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Hearts
 * @date 2019/3/11
 * @desc
 */
public class SQLUtil {

    private static final String  URL = "jdbc:mysql://localhost:3306/";

    private static final String USERNAME= "root";

    private static final String PASSWORD = "root";

    private static final String DATABASE_NAME = "main/java/test";

    private static Connection conn;

    private static Statement stmt;

    static{
        connect();
    }

    public static boolean connect(String dataBaseName){
        return connect(URL,USERNAME,PASSWORD,dataBaseName);
    }

    public static boolean connect(){
        return connect(DATABASE_NAME);
    }

    /**
     * 连接数据库
     * @return
     */
    public static boolean connect(String url,String username,String password,String dataBaseName){
        try {
            conn = DriverManager.getConnection(url+dataBaseName,username,password);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn != null;
    }

    /**
     * 执行查询语句
     * @param sql
     * @return
     */
    public static List<Map<String,String>> query(final String sql) {

        System.out.println(sql);

        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();

        if (conn != null && sql!=null && sql.trim().length()!=0) {

            try {
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

    public static void insertByManyRow(String tableName,Map<String,List> maps){

        if (checkTableName(tableName)){

            StringBuffer sql = new StringBuffer("INSERT INTO "+tableName+"");

            List<String> keys = new ArrayList<>();

            List<List> values = new ArrayList<>();

            List<String> pres = new ArrayList<>();

            List<String> sufs = new ArrayList<>();

            int count = 0;

            for (Map.Entry<String, List> stringListEntry : maps.entrySet()) {
                keys.add(stringListEntry.getKey());

                if (count !=0 && stringListEntry.getValue().size()!=count){
                    System.out.println("数据列不一致");
                    return;
                }

                count = stringListEntry.getValue().size();

                values.add(stringListEntry.getValue());

                pres.add("?");
            }

            if (count == 0){
                return;
            }

            sql.append("(" + String.join(",", keys) + ") VALUES ");

            for (int i = 0; i < count; i++) {
                sufs.add("(" + String.join(",", pres) + ")");
            }

            sql.append(String.join(",",sufs));
            System.out.println(sql.toString());
            try {
                PreparedStatement pstm = conn.prepareStatement(sql.toString());
                for (int i = 0; i < values.size(); i++) {

                    List list = values.get(i);
                    for (int j = 0; j < count; j++) {
                        // 5，5
                        int index = (i+1) + j*values.size();
                        pstm.setObject(index,list.get(j));
                    }
                }
                pstm.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static boolean checkTableName(String tableName){
        return tableName!=null && tableName.trim().length() >0;
    }

    /**
     * 向指定表插入单行数据
     * @param tableName
     * @param map
     */
    public static void insertBySingtonRow(String tableName,Map<String,Object> map){

        if(!checkTableName(tableName)){
            return;
        }

        Map<String,List> lists = new HashMap<>();

        map.forEach((k,v) -> {
            List list = new ArrayList();
            list.add(v);
            lists.put(k,list);
        });

        insertByManyRow(tableName,lists);
    }

    /**
     * 删除数据
     * @param tableName
     * @param conditions
     */
    public static void deleteByCondition(String tableName,List<String> conditions){

        if(!checkTableName(tableName)){
            return;
        }

        StringBuffer sql = new StringBuffer();

        sql.append("DELETE FROM " + tableName);

        if (conditions != null  && conditions.size()>0){

            sql.append(" WHERE  ");

            sql.append(String.join(" and ",conditions));
        }

        System.out.println(sql);

        try {
            stmt.executeUpdate(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据筛选条件更新指定字段信息
     * @param tableName
     * @param updateValues
     * @param conditions
     */
    public static void updateByCondition(String tableName,Map<String,Object> updateValues,List<String> conditions){

        if(!checkTableName(tableName) || updateValues.size()<=0){
            return;
        }

        List values = new ArrayList<>();

        StringBuffer sql = new StringBuffer();

        sql.append("UPDATE "+tableName+" SET ");

        sql.append(String.join(",", updateValues.entrySet().stream().map((entry) -> {
            values.add(entry.getValue());
            return entry.getKey() + "=?";
        }).collect(Collectors.toList())));


        if (conditions != null && conditions.size()>0){
            sql.append(" WHERE ");
            sql.append(String.join(" and ",conditions));
        }

        System.out.println(sql);

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i+1,values.get(i));
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 断开数据库连接
     */
    public static void disconnect(){
        try {
            conn.close();
            conn=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
