package util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import proxy.cglib.ObjectHandler;

import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * @author Hearts
 * @date 2019/5/31
 * @desc
 */
public class ProxyUtil {

    public static <T,R> T  getProxy(Class<T> clazz,ObjectHandler<T,R> handler){

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                T obj = (T) o;
                handler.before(obj);
                R returnValue = (R) methodProxy.invokeSuper(obj,objects);
                handler.after(obj);
                return handler.returnValueHandler(returnValue);
            }
        });
        return (T)enhancer.create();

    }
}
