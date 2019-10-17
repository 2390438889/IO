package util;

import org.junit.Test;

/**
 * @author Hearts
 * @date 2019/4/29
 * @desc
 */
public class FileUtilTest {
    @Test
    public void saveStringToFile(){
        String str = "hello";
        FileUtil.saveStringToFile(str,"F://","a.txt").delete();
    }



}
