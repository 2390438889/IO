package sql;

/**
 * @author Hearts
 * @date 2019/3/11
 * @desc
 */
public class Test {

    public static void queryByTableTest(){
        SQLUtil.queryByTable("book").forEach(item -> {
            System.out.println(String.format("%s -- %s -- %s",item.get("type").split(" ")[0].substring(3),item.get("bookname"),item.get("zip_url")));
        });
    }

    public static void queryByConditionTest(){
        SQLUtil.queryByCondition(new QueryCondition().getBuilder()
                .setTableName("book").setLimit(1, 100).setOrderBy("bookname")
                .setSearchField(new String[]{"type","bookname","zip_url"})
                .setScreenCondition(new String[]{"bookname like '中%'"})
                .build()).forEach(System.out::println);
    }
    public static void main(String[] args) {

        //queryByTableTest();
        queryByConditionTest();
    }
}
