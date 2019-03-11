package util;

import java.io.File;

/**
 * @author Hearts
 * @date 2019/3/10
 * @desc
 */
public interface Classifier {

    String getType(File file);

    void fileProcess(File file,String type);
}
