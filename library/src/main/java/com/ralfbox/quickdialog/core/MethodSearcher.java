package com.ralfbox.quickdialog.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Method;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 31.08.2016.
 */
class MethodSearcher {
    private static final String TAG = MethodSearcher.class.getSimpleName();

    private final Object obj;
    private final Filter filter;

    public MethodSearcher(@NonNull Object obj, @NonNull Filter filter) {
        this.obj = obj;
        this.filter = filter;
    }

    @Nullable
    public Method search() {
        Method [] methods = obj.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (filter.isThisMethod(m))
                return m;
        }

        return null;
    }

    public String getErrMessage(){
        return "Can not find method " + filter.errStringAboutSearchingMethod() +
                "\n\t in class " + obj.getClass().getSimpleName() + ".java" ;
    }

    public interface Filter {
        boolean isThisMethod(Method m);

        String errStringAboutSearchingMethod();
    }
}
