package com.ralfbox.quickdialog.core;

import android.content.DialogInterface;
import android.util.Log;

import com.ralfbox.quickdialog.QuickDialog;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 30.08.2016.
 */
public class CallResultQuickDialogEngineer {

    private static final String TAG = CallResultQuickDialogEngineer.class.getSimpleName();

    private final QuickDialog quickDialog;
    private final MethodSearcher.Filter methodFilter;
    private final int whichButton;

    public CallResultQuickDialogEngineer(QuickDialog quickDialog, int whichButton) {
        this.quickDialog = quickDialog;
        this.whichButton = whichButton;
        this.methodFilter = getMethodFilter(quickDialog, whichButton);
    }

    private static MethodSearcher.Filter getMethodFilter(QuickDialog quickDialog, int whichButton) {
        String requestTag = quickDialog.getRequestTag();
        switch (whichButton){
            case DialogInterface.BUTTON_NEGATIVE:
                return new MethodFilter.NegativeButtonFilter(requestTag);
            case DialogInterface.BUTTON_NEUTRAL:
                return new MethodFilter.NeutralButtonFilter(requestTag);
            case DialogInterface.BUTTON_POSITIVE:
                return new MethodFilter.PositiveButtonFilter(requestTag);
            default:
                return null;
        }
    }

    public void execute() {
        if (methodFilter == null) {
            loge(TAG, "execute: methodFilter is null!");
            return;
        }

        MethodSearcher methodSearcher = new MethodSearcher(quickDialog.getBaseObjectToCallResult(), methodFilter);
        CallMethodEngineer callMethodEngineer = new CallMethodEngineer(
                quickDialog.getBaseObjectToCallResult(),
                methodSearcher, new Object[]{quickDialog, whichButton, quickDialog.getDialog()}
        );

        try {
            callMethodEngineer.execute();
        } catch (Exception e) {
            Log.w(TAG, null, e);
        }
    }

    private static void loge(String tag, String s) {
        Log.e(tag, s);
    }

    private static void loge(String tag, String s, Throwable e) {
        Log.e(tag, s, e);
    }
}
