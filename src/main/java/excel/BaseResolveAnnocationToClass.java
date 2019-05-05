package excel;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
public interface BaseResolveAnnocationToClass {

    boolean vadlite();

    <T> Excel<T>  parse(Class<T> clazz);
}
