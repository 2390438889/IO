package util;

import proxy.cglib.NullObjectFactory;

import javax.management.ObjectInstance;
import java.io.*;

/**
 * @author Hearts
 * @date 2019/4/30
 * @desc
 */
public class ObjectUtil {

    /**
     * 将对象序列化为字节码
     * @param obj
     * @return
     */
    public static byte[] objectToBytes(Object obj){
        byte[] objectBytes = new byte[0];
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            objectBytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectBytes;
    }

    /**
     * 将字节码反序列化为对象
     * @param bytes
     * @return
     */
    public static Object bytesToObject(byte[] bytes){
        Object object =null;
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            object = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 克隆一个对象
     * @param object
     * @return
     */
    public static Object objectClone(Object object){
        byte[] objectBytes = objectToBytes(object);
        return bytesToObject(objectBytes);
    }

    /**
     * 获得一个空对象
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T getNullObject(T obj){
        return (T) NullObjectFactory.getNullObject(obj.getClass());
    }

}
