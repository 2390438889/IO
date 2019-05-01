package io;

import java.io.File;

/**
 * @author Hearts
 * @date 2019/3/9
 * @desc
 */
public class FileClassifier {

    /**
     * 需分类的文件夹
     */
    private File baseDir;

    /**
     * 分类的规则以及分类文件如何处理
     */
    private Classifier classifier;

    public FileClassifier(File baseDir, Classifier classifier) {
        this.baseDir = baseDir;
        this.classifier = classifier;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public void process(){

        if (baseDir.exists()){
            //遍历该文件夹下的所有文件
            File[] files = baseDir.listFiles();

            for (File file : files) {

                //查看该文件所属类别
                String type = classifier.getType(file);

                //该类别文件如何处理
                classifier.fileProcess(file,type);
            }
        }
    }





}
