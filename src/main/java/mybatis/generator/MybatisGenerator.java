package mybatis.generator;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hearts
 * @date 2019/5/17
 * @desc mybatis代码生成器
 */
public final class MybatisGenerator {

    private final InputStream configInputStream;

    public MybatisGenerator(InputStream configInputStream) {
        this.configInputStream = configInputStream;
    }

    public final void generator(){
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            //创建一个配置文件解析器
            ConfigurationParser cp = new ConfigurationParser(warnings);
            //将配置文件解析成Configuration对象
            Configuration config = cp.parseConfiguration(configInputStream);
            //设置文件处理，是否覆盖
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            //创建MybatisGenerator对象
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            //执行生成代码程序
            myBatisGenerator.generate(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
    }
}
