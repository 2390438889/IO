package web.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Hearts
 * @date 2019/4/26
 * @desc
 */
class WebAutoTestInvocation implements InvocationHandler {

    private WebAuto webAuto;

    public WebAutoTestInvocation(WebAuto webAutoTest) {
        this.webAuto = webAutoTest;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        webAuto.sleep();
        return method.invoke(webAuto,args);
    }
}
public class WebAutoProxyFactory {

    public static WebAuto newInstance(WebAuto webAuto){
        return (WebAuto) Proxy.newProxyInstance(WebAutoTest.class.getClassLoader(), new Class[]{WebAuto.class}, new WebAutoTestInvocation(webAuto));
    }
}
