package util;

/**
 * @author Hearts
 * @date 2019/10/17
 * @desc
 */
public class StringUtil {

    private StringUtil(){}

    /**
     * 获取前缀
     * @param str
     * @param split
     * @return
     */
    public static String getPrefix(String str,String split){
        return str.substring(0,str.lastIndexOf(split));
    }

    /**
     * 获取后缀
     * @param str
     * @param split
     * @return
     */
    public static String getSuffix(String str,String split){
        return str.substring(str.lastIndexOf(split)+split.length());
    }
}
