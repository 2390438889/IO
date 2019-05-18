package mybatis.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @author Hearts
 * @date 2019/4/16
 * @desc
 */
public class SimpleCommentGenerator implements CommentGenerator {

    private boolean suppressDate = false;
    private boolean suppressAllComments = false;
    private boolean addRemarkComments = false;
    private SimpleDateFormat dateFormat;
    private StringBuffer classCommentBanner;
    private String delimiter = "\n";


    //我们可以通过官方默认的DefaultCommentGenerator来观察学习，编写我们自己的注释生成器
    public void addConfigurationProperties(Properties properties) {

        //初始化classCommentBanner
        classCommentBanner = new StringBuffer();

        //获取是否阻止生成时间注释，默认为true
        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        //获取是否阻止生成所有注释
        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        //获取是否生成备注注释
        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        //自定义字段，在xml中配置delimiter属性可以自定义注释的分隔符
        if (properties.getProperty("delimiter") != null){
            delimiter = properties.getProperty("delimiter");
        }

        //获取时间格式化的字符串
        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }

        //获取自定义的类注释横幅
        if (properties.getProperty("classCommentBanner") != null){
            String baseClassCommentBanner = properties.getProperty("classCommentBanner");
            //替换${date}字符串为当前格式化时间
            baseClassCommentBanner = baseClassCommentBanner.replace("${date}",getDateString());
            classCommentBanner.append(baseClassCommentBanner);
        }

    }

    /**
     * 获得格式化时间
     * @return
     */
    private String getDateString(){
        if (suppressDate) {
            return null;
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }

    /**
     * 添加字段注释
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        generatorComment(introspectedColumn.getRemarks(),field);
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    /**
     * 添加实体类注释
     * @param topLevelClass
     * @param introspectedTable
     */
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        classCommentBanner.append(introspectedTable.getRemarks());
        generatorComment(classCommentBanner.toString(),topLevelClass);
    }

    /**
     * 添加内部类注释
     * @param innerClass
     * @param introspectedTable
     */
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {
    }

    /**
     * 添加枚举注释
     * @param innerEnum
     * @param introspectedTable
     */
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    /**
     * 添加getter方法注释
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /**
     * 添加setter方法注释
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /**
     * 添加普通方法注释
     * @param method
     * @param introspectedTable
     */
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    /**
     * 添加java文件注释
     * @param compilationUnit
     */
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    /**
     * 添加mapper.xml注释
     * @param xmlElement
     */
    public void addComment(XmlElement xmlElement) {

    }

    public void addRootComment(XmlElement xmlElement) {

    }


    /**
     * 生成备注注释
     * @param remark
     * @param field
     */
    private void generatorComment(String remark,JavaElement field) {
        //如果阻止生成注释
        if (suppressAllComments){
            return;
        }

        final boolean validRemark = StringUtility.stringHasValue(remark);
        //根据参数和备注信息判断是否添加备注
        if (validRemark && addRemarkComments){
            final String[] remarkLine = remark.split(delimiter);

            //开始注释
            field.addJavaDocLine("/**");

            for (String s : remarkLine) {

                field.addJavaDocLine(" * "+s);
            }

            //结束注释
            field.addJavaDocLine(" */");


        }
    }





}
