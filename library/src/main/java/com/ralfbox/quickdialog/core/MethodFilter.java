package com.ralfbox.quickdialog.core;

import android.support.annotation.Nullable;

import com.ralfbox.quickdialog.NegativeButtonQD;
import com.ralfbox.quickdialog.NeutralButtonQD;
import com.ralfbox.quickdialog.PositiveButtonQD;

import java.lang.reflect.Method;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 31.08.2016.
 */
abstract class MethodFilter implements MethodSearcher.Filter{

    private final String requestTag;

    public MethodFilter(String requestTag) {
        this.requestTag = requestTag;
    }

    @Override
    public boolean isThisMethod(Method m) {
        String tag = getRequestTag(m);
        return tag != null && tag.equals(requestTag);
    }

    @Nullable
    protected abstract String getRequestTag(Method m);

    public final String errStringAboutSearchingMethod(){
        return "with annotation: @" + getNameOfAnnotationForErrString() + "(\"" + requestTag + "\")";
    }

    protected abstract String getNameOfAnnotationForErrString();




    static class NegativeButtonFilter extends MethodFilter {

        public NegativeButtonFilter(String requestTag) {
            super(requestTag);
        }

        @Nullable
        @Override
        protected String getRequestTag(Method m) {
            NegativeButtonQD annotation = m.getAnnotation(NegativeButtonQD.class);
            return annotation != null ? annotation.value() : null;
        }

        @Override
        protected String getNameOfAnnotationForErrString() {
            return NegativeButtonQD.class.getSimpleName();
        }

    }




    static class NeutralButtonFilter extends MethodFilter {

        public NeutralButtonFilter(String requestTag) {
            super(requestTag);
        }

        @Nullable
        @Override
        protected String getRequestTag(Method m) {
            NeutralButtonQD annotation = m.getAnnotation(NeutralButtonQD.class);
            return annotation != null ? annotation.value() : null;
        }

        @Override
        protected String getNameOfAnnotationForErrString() {
            return NeutralButtonQD.class.getSimpleName();
        }
    }



    static class PositiveButtonFilter extends MethodFilter {

        public PositiveButtonFilter(String requestTag) {
            super(requestTag);
        }

        @Nullable
        @Override
        protected String getRequestTag(Method m) {
            PositiveButtonQD annotation = m.getAnnotation(PositiveButtonQD.class);
            return annotation != null ? annotation.value() : null;
        }

        @Override
        protected String getNameOfAnnotationForErrString() {
            return PositiveButtonQD.class.getSimpleName();
        }
    }
}
