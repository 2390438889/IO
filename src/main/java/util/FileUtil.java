package util;

import io.file.FileFilterUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Hearts
 * @date 2019/3/10
 * @desc 文件处理工具
 */


public final class FileUtil {

    public interface FileClassifyRule{

        String rule(File file);

    }

    private FileUtil(){}

    /**
     * 创建一个文件
     * @param filePath
     * @return
     */
    public static File createNewFile(String filePath){
        final File file = new File(filePath);
        createNewFile(file);
        return file;
    }

    /**
     * 创建一个文件
     * @param file
     */
    public static void createNewFile(File file){
        File baseDir = file.getParentFile();
        if (!baseDir.exists()){
            baseDir.mkdirs();
        }
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDir(File file){
        final File parent = file.getParentFile();
        if (parent != null && !parent.exists()){
            createDir(parent);
        }
        file.mkdirs();
    }

    /**
     * 创建一个文件
     * @param baseDir
     * @param fileName
     * @return
     */
    public static File createNewFile(File baseDir,String fileName){
        final File file = new File(baseDir,fileName);
        createNewFile(file);
        return file;
    }

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
     * 将文件夹内的文件按指定规则分类
     * @param basePath
     */
    public static void classifierFiles(String basePath,FileClassifyRule rule){
        classifierFiles(new File(basePath),rule);
    }

    /**
     * 将文件夹内的文件按后缀名分类
     * @param basePath
     */

    public static void classifierFiles(File basePath,FileClassifyRule rule){

        //获取所有文件
        File[] files = basePath.listFiles(FileFilterUtils.createFileFilter());

        Map<String,File> createdDirName = new HashMap<>();

        //获取所有的文件夹对象
        File[] dirs = basePath.listFiles(FileFilterUtils.createDictionaryFilter());

        //将已经存在的文件夹记录到map中
        Arrays.stream(dirs).forEach((f)->{
            createdDirName.put(f.getName(),f);
        });

        for (int i = 0; i < files.length; i++) {
            final String type = rule.rule(files[i]);
            File dir = createdDirName.get(type);
            if (dir == null){
                dir = new File(basePath,type);
                //如果该文件夹不存在，则先创建该文件夹
                createdDirName.put(type,dir);
                dir.mkdir();
            }
            copyFileToDir(files[i],dir);
            //删除文件
            files[i].delete();
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
     * @param
     */
    public static void copyFileToDir(File oldFile,File dirFile){
        if (!dirFile.exists()){
            dirFile.mkdirs();
        }
        if (oldFile.isFile() && dirFile.isDirectory()){
            copyFileToFile(oldFile, new File(dirFile, oldFile.getName()));
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
            copyFileToDir(oldFile, newFile);
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

        recursiveProcess(file,(f) -> {
            f.delete();
        });

    }

    public static void showDirs(File file){
        recursiveProcess(file,(f) -> {
            System.out.println(f.getAbsolutePath());
        });
    }

    /**
     * 下载网络文件的前期准备
     * @param downUrl
     * @param dir
     * @return
     * @throws MalformedURLException
     */
    public static URL downPre(String downUrl,File  dir) throws MalformedURLException {
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
            streamSaveByNIO(url.openStream(), new FileOutputStream(new File(dir, fileName)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归处理所有文件
     * @param file
     * @param call
     */
    public static void recursiveProcess(File file,Consumer<File> call){

        if (file.exists()){
            if (file.isDirectory()){
                for (File f:file.listFiles()){
                    recursiveProcess(f,call);
                }
            }
            call.accept(file);
        }
    }


    public static void readToWriteByIO(Reader reader,Writer writer){

        try(BufferedReader br = new BufferedReader(reader);
            BufferedWriter bw = new BufferedWriter(writer)){
            char[] data = new char[1024];
            int length = 0;
            while((length = br.read(data))>0){
                bw.write(data,0,length);
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件中的字符串读取出来
     * @param file
     * @return
     */
    public static String readFileToString(File file,String charset) {
        String str = "";
        if (file!=null && file.isFile()){
            byte[] data = new byte[(int) file.length()];
            try(FileInputStream fis = new FileInputStream(file)){
                fis.read(data);
                str = new String(data,charset);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return str;
    }

    /**
     * 将字符串保存到文件中
     * @param content
     * @param filepath
     * @param filename
     */
    public static File saveStringToFile(String content,String filepath,String filename){

        return saveBytesToFile(content.getBytes(),filepath,filename);
    }

    public static File saveBytesToFile(byte[] data,String filepath,String filename){
        return saveBytesToFile(data,filepath+"/"+filename);
    }

    public static File saveBytesToFile(byte[] data,String filepath){
        File file = createNewFile(filepath);

        FileOutputStream fileOutputStream = null;

        ByteArrayInputStream byteArrayInputStream = null;

        try{
            fileOutputStream = new FileOutputStream(file);
            byteArrayInputStream = new ByteArrayInputStream(data);
            streamSaveByIO(byteArrayInputStream,fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 将字符串保存到文件中
     * @param content
     * @param filepath
     */
    public static File saveStringToFile(String content,String filepath){

        return saveBytesToFile(content.getBytes(),filepath);

    }


    public static void moveByCMD(File file,File dir) throws IOException {
        if (file.exists() && dir.exists() && dir.isDirectory()){
            String exec = "move "+file.getAbsolutePath()+" "+dir.getAbsolutePath();
            Runtime.getRuntime().exec(exec);
        }
    }




}
