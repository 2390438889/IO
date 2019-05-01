package web.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author Hearts
 * @date 2019/4/25
 * @desc
 */

public class WebAutoTest implements WebAuto{

    public static final WebType DEFAULT_DRIVER = WebType.CROMED_DRIVER;

    private WebDriver webDriver;

    private Map<String,String> xpathAndDataMap;

    private long baseSleepTime;

    public WebAutoTest() {
        this(0);
    }

    public WebAutoTest(long baseSleepTime){
        this(DEFAULT_DRIVER,baseSleepTime);
    }

    public WebAutoTest(WebType webType,long baseSleepTime) {
        xpathAndDataMap = new HashMap<String, String>();
        this.baseSleepTime = baseSleepTime;

        webDriver = webType.createWebDriver();
    }

    public static WebAuto getWebAutoTestObject(long baseSleepTime){

        WebAutoTest webAutoTest = new WebAutoTest(baseSleepTime);

        WebAuto webAutoTestProxy = WebAutoProxyFactory.newInstance(webAutoTest);
        return webAutoTestProxy;
    }

    /**
     * 打开浏览器，并设置窗口显示最大化，并删除所有的Cookie
     */
    public void openBrowserInit(long adapteTime,TimeUnit timeUnit){

        webDriver.manage().window().maximize();

        webDriver.manage().deleteAllCookies();

        //与浏览器同步,等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(adapteTime, timeUnit);
    }

    public void openBrowserInit(){
        openBrowserInit(2,TimeUnit.SECONDS);
    }

    /**
     * 获得webDriver对象
     * @return
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    public long getBaseSleepTime() {
        return baseSleepTime;
    }

    public void get(String url){
        getWebDriver().get(url);
    }

    /**
     * 关闭浏览器
     */
    public void close(){
        webDriver.quit();
    }

    /**
     * 填充表单信息
     */
    public void inputFormInfoByXPath(){
        final Set<Map.Entry<String,String>> formInfos = getXpathAndDatas().entrySet();
        for (Map.Entry<String, String> formInfo : formInfos) {
            sendKeys(formInfo.getKey(),formInfo.getValue());
        }
        xpathAndDataClear();
    }

    /**
     * 向指定元素发送消息，常用于填充表单元素
     * @param elementByXpathRule
     * @param message
     */
    public void sendKeys(String elementByXpathRule,String message){
        getWebElement(elementByXpathRule).sendKeys(message);
    }

    public WebElement getWebElement(String rule){
        return getWebDriver().findElement(By.xpath(rule));
    }

    /**
     * 休眠baseSleepTime秒
     */
    public void sleep(){
        this.sleep(getBaseSleepTime());
    }

    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<String,String> getXpathAndDatas(){
        return xpathAndDataMap;
    }

    public void xpathAndDataClear(){
        getXpathAndDatas().clear();
    }

    public void click(String xpath){
        webDriver.findElement(By.xpath(xpath)).click();
    }

    public void addXpathAndData(String xpath,String sendValue){
        getXpathAndDatas().put(xpath,sendValue);
    }


}
