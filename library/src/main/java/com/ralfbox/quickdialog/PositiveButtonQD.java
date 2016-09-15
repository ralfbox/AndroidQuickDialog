package com.ralfbox.quickdialog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 30.08.2016.
 */



@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveButtonQD {
    String value();
}
