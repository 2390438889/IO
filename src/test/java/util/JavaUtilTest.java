package util;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Hearts
 * @date 2019/4/29
 * @desc
 */
public class JavaUtilTest {

    @Test
    public void strSrctoClassTest(){
        final String ln = "\r\n";
        final StringBuffer sb = new StringBuffer();
        final String packageName = FileUtil.class.getPackage().getName();
        final String className = "FileProxyTest";
        final String classpath = FileUtil.class.getClassLoader().getResource("").getPath();
        sb.append("package "+packageName+";"+ln);
        sb.append("public class "+className+"{"+ln);
        sb.append("public static void say(){System.out.println(\">>>>>>>>>>>\");}"+ln);
        sb.append("}"+ln);
        try {
            Class clazz =JavaUtil.stringCompiledToClass(packageName+"."+className,sb.toString(),classpath);
            Method method = clazz.getMethod("say", null);
            method.invoke(null,null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
