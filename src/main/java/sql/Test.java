package sql;

import util.SQLUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hearts
 * @date 2019/3/11
 * @desc
 */
public class Test {

    public static void queryByTableTest(){
        SQLUtil.queryByTable("book").forEach(item -> {
            System.out.println(item);
        });
    }

    public static void queryByConditionTest(){
        SQLUtil.queryByCondition(new QueryCondition().getBuilder()
                .setTableName("`user`")
                .build()).forEach(System.out::println);
    }

    public static void insertByTableTest(){
        String tableName = "`user`";
        Map<String,Object> map = new HashMap<>();
        map.put("username","aa");
        map.put("password", "aabbcc");
        SQLUtil.insertBySingtonRow(tableName, map);
    }

    public static void insertByTableByMany(){
        String tableName = "`user`";
        Map<String,List> map = new HashMap<>();
        map.put("username", Arrays.asList("张三","里斯","王五","赵六"));
        map.put("password", Arrays.asList("hello","world","is","me"));
        SQLUtil.insertByManyRow(tableName, map);
    }

    public static void deleteByConditions(){
        SQLUtil.deleteByCondition("`user`",Arrays.asList("id > 4","username like '%三'"));
    }

    public static void updateByCondition(){
        Map<String,Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("username","徐小平");
        stringObjectMap.put("password","xuxiaoping");
        SQLUtil.updateByCondition("`user`",stringObjectMap,Arrays.asList("id = 4"));
    }
    public static void main(String[] args) {

        queryByTableTest();
        //insertByTableTest();
        //insertByTableByMany();
        //deleteByConditions();
        //updateByCondition();
        //queryByConditionTest();

        SQLUtil.disconnect();

    }
}
