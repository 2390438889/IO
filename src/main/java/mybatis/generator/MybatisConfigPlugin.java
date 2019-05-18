package mybatis.generator;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.XmlConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author Hearts
 * @date 2019/4/22
 * @desc
 */
public class MybatisConfigPlugin extends PluginAdapter {

    private List<String> mapperFiles;

    public MybatisConfigPlugin() {
        this.mapperFiles = new ArrayList<String>();
    }


    public boolean validate(List<String> warnings) {
        boolean valid = true;

        if (!stringHasValue(properties
                .getProperty("targetProject"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetProject")); //$NON-NLS-1$
            valid = false;
        }

        if (!stringHasValue(properties
                .getProperty("targetPackage"))) { //$NON-NLS-1$
            warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                    "MapperConfigPlugin", //$NON-NLS-1$
                    "targetPackage")); //$NON-NLS-1$
            valid = false;
        }

        return valid;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        Document document = new Document(
                XmlConstants.MYBATIS3_MAPPER_CONFIG_PUBLIC_ID,
                XmlConstants.MYBATIS3_MAPPER_CONFIG_SYSTEM_ID);

        XmlElement root = new XmlElement("configuration");
        document.setRootElement(root);

        root.addElement(settingsGenerator());

        if (hasEnviromentProperties()){
            root.addElement(enviromentsElementGenerator());
        }

        XmlElement mappers = new XmlElement("mappers");
        root.addElement(mappers);

        XmlElement mapper;
        for (String mapperFile : mapperFiles) {
            mapper = new XmlElement("mapper");
            mapper.addAttribute(new Attribute("resource", mapperFile));
            mappers.addElement(mapper);
        }

        GeneratedXmlFile gxf = new GeneratedXmlFile(document, properties
                .getProperty("fileName", "MapperConfig.xml"),
                properties.getProperty("targetPackage"),
                properties.getProperty("targetProject"),
                false, context.getXmlFormatter());

        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>(1);
        answer.add(gxf);

        return answer;
    }

    private Element settingsGenerator() {
        XmlElement settings = new XmlElement("settings");
        settings.addElement(settingElementGenerator("logImpl","LOG4J"));
        return settings;
    }


    private List<String> subListByRegex(String regex){
        List<String> subList = new ArrayList<String>();
        for (Object s : properties.keySet()) {
            final String key = s.toString();
            if (key.matches(regex)){
                subList.add(key);
            }
        }
        return subList;
    }

    private boolean hasEnviromentProperties(){
        return hasKeys(subListByRegex("\\w*jdbc\\w*"));
    }

    private boolean hasKeys(List<String> keys){

        for (String key : keys) {
            if (properties.getProperty(key) == null){

                return false;
            }
        }

        return true;
    }

    private Element enviromentsElementGenerator() {
       /*<environments default="development">
        <environment id="development">
            <transactionManager type="JDBC">
            </transactionManager>
            <dataSource type="UNPOOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://192.168.16.137:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value=""/>
            </dataSource>
        </environment>
    </environments>*/

        final XmlElement enviroments = new XmlElement("environments");

        enviroments.addAttribute(attributeGenerator("default","development"));

        final XmlElement enviroment = new XmlElement("environment");

        enviroments.addElement(enviroment);

        enviroment.addAttribute(attributeGenerator("id","development"));

        enviroment.addElement(transactionManagerGenerator());

        enviroment.addElement(dataSourceGenerator());

        return enviroments;
    }

    private Element dataSourceGenerator() {
        final XmlElement dataSource = new XmlElement("dataSource");
        dataSource.addAttribute(attributeGenerator("type", properties.getProperty("attribute-jdbc-type")));
        Map<String,String> dataSourceProperties = propertiesByRegex("property-jdbc.*", "property-jdbc-");
        elementAddProperties(dataSource, dataSourceProperties);
        return dataSource;
    }

    private Map<String,String> propertiesByRegex(String regex,String remove){
        Map<String,String> pro = new HashMap<String, String>();
        final List<String> subList = subListByRegex(regex);
        for (String s : subList) {
            pro.put(s.replace(remove,""),properties.getProperty(s));
        }
        return pro;
    }

    public void elementAddAttributes(final XmlElement element,Map<String,String> maps){
        maps.entrySet().stream()
                .forEach(new Consumer<Map.Entry<String, String>>() {
                    public void accept(Map.Entry<String, String> property) {
                        element.addAttribute(attributeGenerator(property.getKey(),property.getValue()));
                    }
                });
    }



    private void elementAddProperties(final XmlElement element,Map<String,String> maps){
        maps.entrySet().stream()
                .forEach(new Consumer<Map.Entry<String, String>>() {
                    public void accept(Map.Entry<String, String> property) {
                        element.addElement(propertyElementGenerator(property.getKey(), property.getValue()));
                    }
                });
    }

    private Element propertyElementGenerator(String key, String value) {
        return tagEntryElementGenerator(key,value,"property");
    }

    private Element settingElementGenerator(String key,String value){
        return tagEntryElementGenerator(key,value,"setting");
    }

    private Element tagEntryElementGenerator(String name, String value,String tagName) {
        final XmlElement property = new XmlElement(tagName);
        property.addAttribute(attributeGenerator("name",name));
        property.addAttribute(attributeGenerator("value",value));
        return property;
    }


    private Element transactionManagerGenerator() {
        final XmlElement transactionManager = new XmlElement("transactionManager");
        transactionManager.addAttribute(attributeGenerator("type","JDBC"));
        return transactionManager;
    }

    private Attribute attributeGenerator(String key,String value){
        return new Attribute(key,value);
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap,
                                   IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        sb.append(sqlMap.getTargetPackage());
        sb.append('.');
        String temp = sb.toString();
        sb.setLength(0);
        sb.append(temp.replace('.', '/'));
        sb.append(sqlMap.getFileName());
        mapperFiles.add(sb.toString());

        return true;
    }
}
