package web.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Hearts
 * @date 2019/4/26
 * @desc
 */
public interface WebAuto {
    /**
     * 打开浏览器，并设置窗口显示最大化，并删除所有的Cookie
     */
    public void openBrowserInit(long adapteTime,TimeUnit timeUnit);

    public void openBrowserInit();

    /**
     * 获得webDriver对象
     * @return
     */
    public WebDriver getWebDriver();

    public long getBaseSleepTime();

    public void get(String url);

    /**
     * 关闭浏览器
     */
    public void close();

    /**
     * 填充表单信息
     */
    public void inputFormInfoByXPath();

    /**
     * 向指定元素发送消息，常用于填充表单元素
     * @param elementByXpathRule
     * @param message
     */
    public void sendKeys(String elementByXpathRule,String message);

    public WebElement getWebElement(String rule);
    /**
     * 休眠baseSleepTime秒
     */
    public void sleep();

    public void sleep(long time);

    public void xpathAndDataClear();

    public void click(String xpath);

    public void addXpathAndData(String xpath,String sendValue);

}
