package ru.sbt.spring_study.parser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 1 on 23.07.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AssociatedFileExt {
    String fileExt();
}
