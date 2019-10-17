package util;

import org.junit.Test;

/**
 * @author Hearts
 * @date 2019/6/17
 * @desc
 */
public class StringUtilTest {

    public static void main(String[] args) {
        String s = "569549 Docker 容器与容器云（第2版）@www.java1234.com.pdf";
        System.out.println(s.replaceAll("[,@\\[]\\[*www.java1234.com",""));
    }

    @Test
    public void suffixTest(){
        System.out.println(StringUtil.getSuffix("aa.txt","."));
    }
}
