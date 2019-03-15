package util;

import util.base.StringParse;
import util.base.impl.TextStringParse;

import java.util.*;

/**
 * @author Hearts
 * @date 2019/3/14
 * @desc
 */
public class StringUtil{

    private final static StringParse textStringParse = new TextStringParse();


    public static Map<String,List<String>> textStringParse(String str){
        return textStringParse.parse(str);
    }

}
