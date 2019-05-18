package excel.typehandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class TypeHandlerFacotry {

    private static Map<Class,TypeHandler> typeHandlerMap;

    static {
        typeHandlerMap = new HashMap<>();
        typeHandlerMap.put(Integer.class,new IntegerTypeHandler());
        typeHandlerMap.put(Double.class,new DoubleTypeHandler());
        typeHandlerMap.put(String.class,new StringTypeHandler());
        typeHandlerMap.put(Date.class,new DateTypeHandler());
    }

    public static TypeHandler getTypeHandler(Class clazz){
        return typeHandlerMap.get(clazz);
    }
}
