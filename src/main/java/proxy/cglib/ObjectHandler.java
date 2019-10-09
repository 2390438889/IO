package proxy.cglib;

/**
 * @author Hearts
 * @date 2019/5/31
 * @desc
 */
public interface ObjectHandler<T,R> {

    void before(T obj);

    void after(T obj);

    R returnValueHandler(R r);
}
