package web.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Hearts
 * @date 2019/4/25
 * @desc
 */
public enum WebType{
    CROMED_DRIVER("webdriver.chrome.driver","src/main/resources/chromedriver.exe")
    ,FIREFOX_DRIVER("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");

    WebType(String systemPropertyName, String systemProertyValue) {
        System.setProperty(systemPropertyName,systemProertyValue);
    }

    public WebDriver createWebDriver(){
        final String name = this.name();
        if (name.equals("CROMED_DRIVER")){
            return new ChromeDriver();
        }else if (name.equals("FIREFOX_DRIVER")){
            return new FirefoxDriver();
        }

        return new ChromeDriver();
    }
}