package com.jd.plugin.set;

/**
 * @author: wangyingjie1
 * @version: 1.0
 * @createdate: 2017-11-20 14:20
 */
public class StatementGenerator {


    public static String defaultGetFormat = "/**\n" +
            " * 获取 #{bare_field_comment}\n" +
            " * \n" +
            " * @return ${field.name} #{bare_field_comment}  \n" +
            " */ ";
    public static String defaultSetFormat = "/**\n" +
            " * 设置 #{bare_field_comment}\n" +
            " * \n" +
            " * @param ${field.name} #{bare_field_comment}  \n" +
            " */ ";
}