package sql;

/**
 * @author Hearts
 * @date 2019/3/11
 * @desc
 */
public class Test {

    public static void queryAllTest(){
        SQLUtil.queryAll("book").forEach(item -> {
            System.out.println(String.format("%s -- %s -- %s",item.get("type").split(" ")[0].substring(3),item.get("bookname"),item.get("zip_url")));
        });
    }
    public static void main(String[] args) {

        queryAllTest();

    }
}
