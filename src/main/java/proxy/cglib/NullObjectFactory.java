package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Hearts
 * @date 2019/4/30
 * @desc
 */
public class NullObjectFactory {

    public static Object getNullObject(Class clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                final Class returnType = method.getReturnType();
                final boolean isNotVoid = !returnType.getName().equals("void");

                try {
                    if (isNotVoid) {
                        if (returnType.isPrimitive()){

                        }else if (returnType.getConstructor(new Class[]{})!=null){
                            return returnType.newInstance();
                        }
                    }
                } catch (NoSuchMethodException ex) {
                    System.out.println(boolean.class.isPrimitive());
                    return boolean.class.isPrimitive();
                }
                return null;
            }
        });
        return enhancer.create();
    }
}
