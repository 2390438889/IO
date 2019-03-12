package util;

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
        FileUtil.copyFileToFile(new File("F:\\书籍\\奥术神座.txt"),new File("F:\\a.txt"));
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
    public static void fileClassifier(){
        FileUtil.fileClassiferByType(new File("F:\\书籍 - 副本"));
    }
    public static void downFileByIOTest(){
        FileUtil.downFileByIO("http://www.555x.org/home/down/txt/id/44766", new File("F:\\aa"), "aa.txt");
    }
    public static void downFileByNIOTest(){
        FileUtil.downFileByNIO("http://www.555x.org/home/down/txt/id/44766", new File("F:\\aa"), "aa.txt");
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
        //fileClassifier();
        //downFileByIOTest();
        //downFileByNIOTest();
        //showFilesTest();
        FileUtil.recursiveProcess(new File("F:\\小说"),(f) -> {
            if (f.length() < 1000){
                System.out.println(f.getAbsolutePath());
            }
        });
    }
}
