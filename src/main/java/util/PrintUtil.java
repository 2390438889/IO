package util;

import java.io.PrintStream;

/**
 * @author Hearts
 * @date 2019/4/25
 * @desc
 */
public class PrintUtil {

    private static PrintStream printStream = System.out;

    public static void setPrintStream(PrintStream printStream){
        PrintUtil.printStream = printStream;
    }

    private static PrintStream getPrintStream(){
        return PrintUtil.printStream;
    }

    /**
     * StringFormat 格式化打印
     * @param str
     * @param objects
     */
    public static void formatPrint(String str,Object ... objects){
        getPrintStream().println(String.format(str, objects));
    }

}
