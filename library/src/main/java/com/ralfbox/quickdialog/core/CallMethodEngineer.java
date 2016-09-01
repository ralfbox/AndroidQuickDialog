package com.ralfbox.quickdialog.core;

import java.lang.reflect.Method;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 31.08.2016.
 */
class CallMethodEngineer {

    private final Object mainObject;
    private final MethodSearcher methodSearcher;
    private final Object [] objectsForInvoke;

    public CallMethodEngineer(Object mainObject, MethodSearcher methodSearcher, Object[] objectsForInvoke) {
        this.mainObject = mainObject;
        this.methodSearcher = methodSearcher;
        this.objectsForInvoke = objectsForInvoke;
    }

    public void execute() throws Exception{
        Method method = methodSearcher.search();
        if (method == null)
            throw new Exception(methodSearcher.getErrMessage());

        ResultBuilder resultBuilder = new ResultBuilder(
                method,
                objectsForInvoke
        );

        method.setAccessible(true);
        try {
            method.invoke(mainObject, resultBuilder.buildResults());
        }catch (Exception e){
            if (resultBuilder.getErrors().isEmpty())
                throw e;
            throw new Exception(resultBuilder.getErrors());
        }
    }
}
