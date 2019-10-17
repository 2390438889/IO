package io;

import util.FileUtil;
import util.StringUtil;

import java.io.*;

/**
 * @author Hearts
 * @date 2019/3/10
 * @desc
 */
public class FileTest {

    public static void fileExistsTest(){
        File file = new File("I:\\a.txt");
        System.out.println(file.exists());
    }

    public static void bufferedInputStreamTest(){
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:\\书籍\\奥术神座.txt"))){
            byte[] data = new byte[1024];
            int i;
            while ((i=bis.read(data))>0){

                System.out.println(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFileToFileTest(){
        FileUtil.copyFileToFile(new File("F:\\书籍\\奥术神座.txt"), new File("F:\\a.txt"));
    }

    public static void copyFileToDirTest(){
        FileUtil.copyFileToDir(new File("F:\\书籍\\奥术神座.txt"), new File("F:\\a\\b"));
    }
    public static void copyDirTest(){
        FileUtil.copyDir(new File("F:\\书籍"),new File("F:\\a"));
    }
    public static void copyDirsTest(){
        FileUtil.copyDirs(new File("F:\\书籍\\奥术神座.txt"),new File("F:\\a"));
    }
    public static void delDirsTest(){
        FileUtil.delDirs(new File("F:\\qycache - 副本"));
    }
    public static void moveDirsTest(){
        FileUtil.moveDirs(new File("G:\\python1"),new File("F:\\a"));
    }
    public static void fileClassifier(String path){
        FileUtil.classifierFiles(path, new FileUtil.FileClassifyRule() {
            @Override
            public String rule(File file) {
                final long size = file.length()/1024;
                System.out.println("正在移动    "+
                        "    "+file.getName()+
                        "    "+size+"KB|");
                return StringUtil.getSuffix(file.getName(),".");
            }
        });
    }
    public static void downFileByIOTest(){
        FileUtil.downFileByIO("http://www.555x.org/home/down/txt/main.java.util/44766", new File("F:\\aa"), "aa.txt");
    }
    public static void downFileByNIOTest(){
        FileUtil.downFileByNIO("http://www.555x.org/home/down/txt/main.java.util/44766", new File("F:\\aa"), "aa.txt");
    }
    public static void showFilesTest(){
        FileUtil.showDirs(new File("F:\\qycache"));
    }

    public static void main(String[] args) {
        //fileExistsTest();
        //bufferedInputStreamTest();
        //copyFileToFileTest();
        //copyFileToDirTest();
        //copyDirTest();
        //copyDirsTest();
        //delDirsTest();
        //moveDirsTest();
        for (String arg : args) {
            fileClassifier(arg);
            System.out.println(arg+"  处理完毕");
        }

        //downFileByIOTest();
        //downFileByNIOTest();
        //showFilesTest();
        //fileToStringTest();
    }
}
