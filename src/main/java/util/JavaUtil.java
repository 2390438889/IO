package util;

import javax.tools.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @author Hearts
 * @date 2019/4/28
 * @desc java工具类
 */
public class JavaUtil {

    /**
     * 字符串编译成Class对象
     * @param className
     * @param code
     * @param classpath
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> stringCompiledToClass(String className,String code,String classpath) throws ClassNotFoundException {
        Class<?> clazz = null;
        //获得系统编译器
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        //从编译器中获取标准java文件管理器
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        StrSrcJavaObject strSrcJavaObject = new StrSrcJavaObject(className, code);
        //获得文件对象
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(strSrcJavaObject);
        //设置参数和class文件生成路径
        Iterable<String> options =Arrays.asList("-d", classpath + File.separator);
        JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(null,standardJavaFileManager,null,options,null,fileObjects);
        if (compilationTask.call()){
            clazz = Class.forName(className);
        }
        return clazz;
    }


    private static class StrSrcJavaObject extends SimpleJavaFileObject {
        private String content;
        StrSrcJavaObject(String name, String content) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }
}
