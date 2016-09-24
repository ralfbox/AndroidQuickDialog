package com.ralfbox.quickdialog;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * @author Rafal Pudelko
 * Created by Admin on 15.09.2016.
 */
public abstract class ControllerQD {

    @Deprecated
    public AlertDialog.Builder onCreateBuilder(AlertDialog.Builder builder) {
        return builder;
    }

    public AlertDialog.Builder onCreateBuilder(AlertDialog.Builder builder, Bundle savedInstanceState){
        return onCreateBuilder(builder);
    }

    public void onResume(){

    }

    public void onPause(){

    }

    public void onCreate(QuickDialog quickDialog, Bundle savedInstanceState) {

    }

}
