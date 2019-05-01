package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hearts
 * @date 2019/4/30
 * @desc
 */
public class ObjectUtilTest {

    @Test
    public void cloneTest(){
        List<String> strs = new ArrayList<>();
        strs.add("aa");
        List<String> clone = (List<String>) ObjectUtil.objectClone(strs);
        System.out.println(String.format("%s --> %s",strs,clone));
        System.out.println(String.format("%s == %s --> %s",strs,clone,strs == clone));
    }

    @Test
    public void getNullObjectTest(){
        List<String> strs = new ArrayList<>();
        List<String> proxy = ObjectUtil.getNullObject(strs);
        proxy.add(1,"1");
        proxy.add("11");
        System.out.println(proxy.toString());
    }
}
