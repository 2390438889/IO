package test;


import util.FileUtil;
import util.SQLUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hearts
 * @date 2019/3/11
 * @desc
 */
public class DownBook {

    public static List<Map<String,String>> books;

    public static ExecutorService executorService;

    public static void pre(){
        books = SQLUtil.queryByTable("book");
        executorService = Executors.newFixedThreadPool(20);
    }

    public static void execute(){
        for (Map<String, String> book : books) {
            executorService.submit(() ->{
                String dir = book.get("type");
                dir = dir.split(" ")[0].substring(3);
                if ("".equals(dir.trim())){
                    dir = "F:\\小说";
                }else{
                    dir = "F:\\小说\\"+dir;
                }
                String bookname = book.get("bookname")+".zip";
                String zip_url = book.get("zip_url");
                if (!(new File(dir,bookname).exists())){
                    System.out.println(String.format("download >>> %s  --  %s  --  %s",bookname,dir,zip_url));
                    FileUtil.downFileByNIO(zip_url, new File(dir), bookname);
                }

            });
        }
    }

    public static void main(String[] args) {
        pre();
        execute();
    }
}
