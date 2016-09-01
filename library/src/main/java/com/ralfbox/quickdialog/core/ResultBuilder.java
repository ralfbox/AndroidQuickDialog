package com.ralfbox.quickdialog.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 31.08.2016.
 */
class ResultBuilder {

    private final Method method;
    private final List<Object> resultObjects;
    private StringBuilder errs;

    public ResultBuilder(Method method, Object[] resultObjects) {
        this.method = method;
        this.resultObjects = new ArrayList<>(Arrays.asList(resultObjects));
    }

    public Object [] buildResults(){
        errs = new StringBuilder();

        Class<?>[] parameters = method.getParameterTypes();
        int len = parameters.length;
        Object[] ret = new Object[len];

        for (int i = 0; i < len; i++){
            Class parameter = parameters[i];
            Object result = getResultObject(parameter);
            if (result == null)
                errs.append("Cannot find " + i + " parameter of class " + parameter.getSimpleName() + "\t\n");
            ret[i] = result;
        }
        return ret;
    }

    private Object getResultObject(Class parameter) {
        if (parameter == int.class) parameter = Integer.class;
        else if (parameter == long.class) parameter = Long.class;
        else if (parameter == float.class) parameter = Float.class;
        else if (parameter == double.class) parameter = Double.class;
        else if (parameter == short.class) parameter = Short.class;
        else if (parameter == char.class) parameter = Character.class;
        else if (parameter == byte.class) parameter = Byte.class;
        else if (parameter == boolean.class) parameter = Boolean.class;

        Object ret = null;
        int ixRetObj = 0;
        for (Object o : resultObjects) {
            if (parameter.isInstance(o)) {
                ret = o;
                break;
            }
            ixRetObj++;
        }
        if (ret != null){
            resultObjects.remove(ret);
            resultObjects.add(ret);
        }
        return ret;
    }

    public String getErrors(){
        return errs != null && errs.length() > 0 ?
                "Cannot find all parameters of method " + method.getName() + "\t\n" + errs.toString() :
                "";
    }
}
