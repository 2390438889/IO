package main.java.io;


import util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Hearts
 * @date 2019/4/4
 * @desc
 */
public class Test {
    public static void main(String[] args) {
        try {
            FileUtil.moveByCMD(new File("F:\\下载\\jdk-8u201-linux-x64.tar.gz"), new File("F:\\下载\\gz"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
