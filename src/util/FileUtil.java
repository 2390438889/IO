package util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Hearts
 * @date 2019/3/10
 * @desc 文件处理工具
 */
public class FileUtil {

    private static FileClassifier fileClassifier = new FileClassifier(new File(""),new Classifier(){

        @Override
        public String getType(File file) {
            String returnValue = "无";

            if (file.isDirectory()){
                returnValue = "文件夹";
            }else{
                int sufIndex = file.getName().lastIndexOf('.');
                if (sufIndex >= 0 && sufIndex < file.getName().length()-1){
                    returnValue = file.getName().substring(sufIndex+1);
                }
            }
            return returnValue;
        }

        @Override
        public void fileProcess(File file, String type) {
            if (file.exists() && file.isFile()){
                File dir = new File(file.getParentFile(),type);
                if (!dir.exists()){
                    dir.mkdir();
                }
                moveDirs(file,dir);
            }
        }
    });

    /**
     * 复制一个文件
     * @param oldFile
     * @param newFile
     */
    public static void copy(File oldFile,File newFile){

        if (oldFile.isFile()){

            //如果newFile是一个文件夹,则将文件复制到文件夹中
            copyFileToDir(oldFile,newFile);

            //如果newFile是一个文件,则将文件复制到文件中
            copyFileToFile(oldFile,newFile);
        }
    }

    /**
     * 将文件中的内容复制到新文件中
     * @param oldFile
     * @param newFile
     */
    public static void copyFileToFile(File oldFile,File newFile){
        if (!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (oldFile.isFile() && newFile.isFile()){
            try {
                streamSaveByIO(new FileInputStream(oldFile),new FileOutputStream(newFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将输入流中的数据存到输出流所指向的文件中
     * @param in
     * @param out
     */
    public static void streamSaveByIO(InputStream in,OutputStream out){
        try(BufferedInputStream bis = new BufferedInputStream(in);
            BufferedOutputStream bos = new BufferedOutputStream(out)){
            byte[] data = new byte[1024];
            int i=0;
            while ((i=bis.read(data))>0){
                bos.write(data,0,i);
            }
            bos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * NIO将输入流中的数据存到输出流所指向的文件中
     * @param in
     * @param out
     */
    public static void streamSaveByNIO(InputStream in,FileOutputStream out){

        try(ReadableByteChannel rbc = Channels.newChannel(in);
            FileChannel fouc = out.getChannel()) {
            fouc.transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件到文件夹
     * @param oldFile
     * @param newFile
     */
    public static void copyFileToDir(File oldFile,File newFile){
        if (!newFile.exists()){
            newFile.mkdirs();
        }
        if (oldFile.isFile() && newFile.isDirectory()){
            copyFileToFile(oldFile, new File(newFile, oldFile.getName()));
        }
    }

    /**
     * 复制文件夹(不包括文件夹内的文件)到新的文件夹
     * @param oldDir
     * @param newDir
     */
    public static void copyDir(File oldDir,File newDir){
        if (!newDir.exists()){
            newDir.mkdirs();
        }
        if (oldDir.isDirectory() && newDir.isDirectory()){
            new File(newDir,oldDir.getName()).mkdirs();
        }
    }

    /**
     * 完整的复制某个文件夹到新的文件夹下
     * @param oldDir
     * @param newDir
     */
    public static void copyDirs(File oldDir,File newDir){
        if (!newDir.exists()){
            newDir.mkdirs();
        }
        if (oldDir.isDirectory()){
            copyDir(oldDir, newDir);
            File[] files = oldDir.listFiles();
            for (File file : files) {
                copyDirs(file,new File(newDir,oldDir.getName()));
            }
        }
        if (oldDir.isFile()){
            copyFileToDir(oldDir, newDir);
        }
    }

    /**
     * 将文件移动到新文件夹
     * @param oldFile
     * @param newFile
     */
    public static void moveFile(File oldFile,File newFile){
        if (oldFile.exists()){
            copyFileToDir(oldFile,newFile);
            oldFile.delete();
        }
    }


    /**
     * 将文件夹或文件完整的移动的新文件夹
     * @param oldDir
     * @param newDir
     */
    public static void moveDirs(File oldDir,File newDir){
        if (oldDir.exists()){
            copyDirs(oldDir, newDir);
            delDirs(oldDir);
        }
    }

    /**
     * 删除文件夹内所有的文件
     * @param file
     */
    public static void delDirs(File file){

        if (file.exists()){
            if (file.isDirectory()){
                File[] files = file.listFiles();
                for (File f : files) {
                    delDirs(f);
                }
            }
            file.delete();

        }

    }

    /**
     * 下载网络文件的前期准备
     * @param downUrl
     * @param dir
     * @return
     * @throws MalformedURLException
     */
    private static URL downPre(String downUrl,File  dir) throws MalformedURLException {
        URL url = new URL(downUrl);
        if (!dir.exists() || dir.isFile()){
            dir.mkdirs();
        }
        return url;
    }

    /**
     * 用传统IO流下载网络文件
     * @param downUrl
     * @param dir
     * @param fileName
     */
    public static void downFileByIO(String downUrl,File dir,String fileName){

        try {
            URL url = downPre(downUrl,dir);
            streamSaveByIO(url.openStream(), new FileOutputStream(new File(dir, fileName)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用NIO下载网络文件
     * @param downUrl
     * @param dir
     * @param fileName
     */
    public static void downFileByNIO(String downUrl,File dir,String fileName){

        try {
            URL url = downPre(downUrl,dir);
            streamSaveByIO(url.openStream(), new FileOutputStream(new File(dir, fileName)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件夹内的文件按后缀名分类
     * @param baseDir
     */
    public static void fileClassiferByType(File baseDir){
        fileClassifier.setBaseDir(baseDir);
        fileClassifier.process();
    }




}
