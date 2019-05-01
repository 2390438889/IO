package util.base.impl;


import util.base.StringParse;

import java.util.*;

/**
 * @author Hearts
 * @date 2019/3/14
 * @desc
 */
public class TextStringParse implements StringParse {
    @Override
    public Map<String, List<String>> parse(String str) {

        Map<String,List<String>> map = new HashMap<>();
        Arrays.asList(str.split("\n")).forEach(item -> {

            if (item.matches("[^:]*[:：]([^:])*")){
                String[] strs = item.split("[:：]");
                List<String> values = new ArrayList<String>();
                for (String s : strs[1].split("[,，]")) {
                    if (s.trim().length() >0){
                        values.add(s.trim());
                    }
                }
                if (strs[0].trim().length()>0){
                    map.put(strs[0].trim(),values);
                }
            }
        });

        return map;
    }
}
