package util;

import org.junit.Test;

/**
 * @author Hearts
 * @date 2019/4/29
 * @desc
 */
public class ChaineseNameWorkerTest {
    @Test
    public void generator(){
        for (int i = 0; i < 100; i++) {
            System.out.println(ChaineseNameWorker.generator());
        }
    }
}
