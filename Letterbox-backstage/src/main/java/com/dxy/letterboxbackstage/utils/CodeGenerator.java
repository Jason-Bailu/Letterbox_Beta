package com.dxy.letterboxbackstage.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @Author: JasonD
 * @date: 2023/5/2 22:44
 * @Description: mp代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }

    private static void generator() {
        FastAutoGenerator.create("jdbc:mysql://ip:port/letterbox?serverTimezone=GMT%2b8", "username", "password")
                .globalConfig(builder -> {
                    builder.author("author")//作者
                            .enableSwagger()//开启swagger模型
                            .fileOverride()//覆盖已生成的文件
                            .outputDir("outputdir");//输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.dxy.letterboxbackstage")
                            .moduleName(null)
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "mapper path"));//设置mapperxml路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableHyphenStyle()
                                    .enableRestStyle();
                    builder.addInclude("t_good")//设置需要生成的表名
                            .addTablePrefix("t_");//设置过滤的前缀
                })
                //.templateEngine(new FreemarkerTemplateEngine()) //这里使用了Freemarker引擎模版，默认是Velocity引擎模版
                .execute();
    }
}
