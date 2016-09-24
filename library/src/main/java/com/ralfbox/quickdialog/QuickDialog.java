package com.ralfbox.quickdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ralfbox.quickdialog.core.CallResultQuickDialogEngineer;

import java.lang.reflect.Constructor;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 30.08.2016.
 */
public class QuickDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private static final String ARG_TITLE = "qd-title";
    private static final String ARG_MESSAGE = "qd-message";
    private static final String ARG_CANCELLABLE = "qd-cancellable";
    private static final String ARG_POSITIVE_BUTTON = "qd-bt-positive";
    private static final String ARG_NEGATIVE_BUTTON = "qd-bt-negative";
    private static final String ARG_NEUTRAL_BUTTON = "qd-bt-neutral";
    private static final String ARG_STYLE = "qd-style";

    private static final String ARG_RESULT_TO_ACTIVITY = "qd-resutl-to-activity";
    private static final String ARG_FRAGMENT_ID = "qd-fragment-id";
    private static final String ARG_FRAGMENT_TAG = "qd-fragment-tag";

    private static final String ARG_REQUEST_TAG = "qd-tag-quick-alert";
    private static final String ARG_FINISH_ACTIVITY_IF_POSITIVE_CLICKED = "qd-finish-positive";
    private static final String ARG_FINISH_ACTIVITY_IF_NEGATIVE_CLICKED = "qd-finish-negative";
    private static final String ARG_FINISH_ACTIVITY_IF_NEUTRAL_CLICKED = "qd-finish-neutral";
    private static final String ARG_CONTROLLER = "qd-controllerQD";
    private static final String TAG = QuickDialog.class.getSimpleName();

    private String requestTag;
    private boolean resultToActivity;
    private String resultFragmentTag;
    private int resultFragmentId;
    private ControllerQD controllerQD;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String controolerNameClass = args.getString(ARG_CONTROLLER);
        if (controolerNameClass != null)
            createController(controolerNameClass);

        init(args);
        if (controllerQD != null) controllerQD.onCreate(this, savedInstanceState);

        return buildDialog(args).create();
    }



    private void init(Bundle args) {
        setCancelable(args.getBoolean(ARG_CANCELLABLE, true));
        requestTag = args.getString(ARG_REQUEST_TAG);
        resultToActivity = args.getBoolean(ARG_RESULT_TO_ACTIVITY, true);
        if (!resultToActivity){
            if (args.containsKey(ARG_FRAGMENT_TAG)) resultFragmentTag = args.getString(ARG_FRAGMENT_TAG);
            else resultFragmentId = args.getInt(ARG_FRAGMENT_ID);
        }
    }

    private AlertDialog.Builder buildDialog(Bundle bundle){
        CharSequence tmp;
        //MyBundle bundle = new MyBundle(getContext(), args);

        AlertDialog.Builder ret = bundle.containsKey(ARG_STYLE) ?
                new AlertDialog.Builder(getActivity(), bundle.getInt(ARG_STYLE)) :
                new AlertDialog.Builder(getActivity());

        if ( (tmp = bundle.getCharSequence(ARG_TITLE)) != null) ret.setTitle(tmp);
        if ( (tmp = bundle.getCharSequence(ARG_MESSAGE)) != null) ret.setMessage(tmp);

        if ( (tmp = bundle.getCharSequence(ARG_POSITIVE_BUTTON)) != null) ret.setPositiveButton(tmp, this);
        if ( (tmp = bundle.getCharSequence(ARG_NEGATIVE_BUTTON)) != null) ret.setNegativeButton(tmp, this);
        if ( (tmp = bundle.getCharSequence(ARG_NEUTRAL_BUTTON)) != null) ret.setNeutralButton(tmp, this);
        if (controllerQD != null) ret = controllerQD.onCreateBuilder(ret);
        return ret;
    }

    @Override
    public void onPause() {
        if (controllerQD != null) controllerQD.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (controllerQD != null) controllerQD.onResume();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (    (which == DialogInterface.BUTTON_POSITIVE && getArguments().getBoolean(ARG_FINISH_ACTIVITY_IF_POSITIVE_CLICKED, false)) ||
                (which == DialogInterface.BUTTON_NEGATIVE && getArguments().getBoolean(ARG_FINISH_ACTIVITY_IF_NEGATIVE_CLICKED, false)) ||
                (which == DialogInterface.BUTTON_NEUTRAL && getArguments().getBoolean(ARG_FINISH_ACTIVITY_IF_NEUTRAL_CLICKED, false))){
            getActivity().finish();
        }else{
            new CallResultQuickDialogEngineer(this, which).execute();
        }
    }

    public Object getBaseObjectToCallResult() {
        if (resultToActivity) return getActivity();
        if (resultFragmentTag != null)
            return getFragmentManager().findFragmentByTag(resultFragmentTag);
        return getFragmentManager().findFragmentById(resultFragmentId);
    }

    public String getRequestTag() {
        return requestTag;
    }



    private void createController(String controllerNameClass) {
        try {
            Class<?> c = Class.forName(controllerNameClass);
            Constructor constructor = c.getConstructor();
            Object obj = constructor.newInstance();
            controllerQD = (ControllerQD) obj;

        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Can not found ControllerQD class: " + controllerNameClass);

        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Controller must have a constructor without parameters, and must be a public static class, " + e.getMessage());

        } catch (IllegalAccessException e) {
            Log.e(TAG, "Controller constructor must be a public");

        } catch (ClassCastException e) {
            Log.e(TAG, "The controller must inherit " + ControllerQD.class.getSimpleName());

        } catch (Exception e) {
            Log.e(TAG, null, e);
        }
    }

    public ControllerQD getControllerQD() {
        return controllerQD;
    }


    public static class Builder{
        private final Resources resources;
        private final Bundle bundle;
        private final String tag;

        public Builder(Context context, String requestTag){
            this(context, requestTag, true);
        }

        public Builder(Fragment fragment, String requestTag){
            this(fragment.getContext(), requestTag, false);
            String tagFragment = fragment.getTag();

            if (tagFragment == null) bundle.putInt(ARG_FRAGMENT_ID, fragment.getId());
            else bundle.putString(ARG_FRAGMENT_TAG, tagFragment);
        }

        private Builder(Context context, String tag, boolean toActivity) {
            resources = context.getResources();
            this.tag = tag;
            bundle = new Bundle();
            bundle.putBoolean(ARG_RESULT_TO_ACTIVITY, true);
            bundle.putString(ARG_REQUEST_TAG, tag);
            bundle.putBoolean(ARG_RESULT_TO_ACTIVITY, toActivity);
        }

        public Builder title(CharSequence title){
            return putCharSequence(ARG_TITLE, title);
        }

        public Builder title(@StringRes int title){
            return title(getText(title));
        }

        public Builder message(CharSequence message) {
            return putCharSequence(ARG_MESSAGE, message);
        }

        public Builder message(@StringRes int message){
            return message(getText(message));
        }

        public Builder positiveButton(CharSequence positiveButton) {
            return putCharSequence(ARG_POSITIVE_BUTTON, positiveButton);
        }

        public Builder positiveButton(@StringRes int positiveButton) {
            return positiveButton(getText(positiveButton));
        }

        public Builder negativeButton(CharSequence negativeButton) {
            return putCharSequence(ARG_NEGATIVE_BUTTON, negativeButton);
        }

        public Builder negativeButton(@StringRes int negativeButton) {
            return negativeButton(getText(negativeButton));
        }

        public Builder neutralButton(CharSequence neutralButton) {
            return putCharSequence(ARG_NEUTRAL_BUTTON, neutralButton);
        }

        public Builder neutralButton(@StringRes int neutralButton) {
            return neutralButton(getText(neutralButton));
        }

        public Builder finishActivityIfPositiveBTClicked(){
            bundle.putBoolean(ARG_FINISH_ACTIVITY_IF_POSITIVE_CLICKED, true);
            return this;
        }

        public Builder finishActivityIfNegativeBTClicked(){
            bundle.putBoolean(ARG_FINISH_ACTIVITY_IF_NEGATIVE_CLICKED, true);
            return this;
        }

        public Builder finishActivityIfNeutralBTClicked(){
            bundle.putBoolean(ARG_FINISH_ACTIVITY_IF_NEUTRAL_CLICKED, true);
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            bundle.putBoolean(ARG_CANCELLABLE, cancelable);
            return this;
        }

        public Builder controller(Class<? extends ControllerQD> controllerClass){
            bundle.putString(ARG_CONTROLLER, controllerClass.getName());
            return this;
        }

        public Bundle getBundle(){
            return bundle;
        }

        public Builder putAnyCharSequence(String key, CharSequence value) {
            bundle.putCharSequence(key, value);
            return this;
        }

        public Builder putAnyString(String key, String value) {
            bundle.putString(key, value);
            return this;
        }

        public Builder putAnyInt(String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        public QuickDialog build(){
            QuickDialog ret = new QuickDialog();
            ret.setArguments(bundle);
            return ret;
        }

        public void show(FragmentManager fragmentManager){
            build().show(fragmentManager, tag);
        }

        private Builder putCharSequence(String key, CharSequence text) {
            bundle.putCharSequence(key, text);
            return this;
        }

        private CharSequence getText(@StringRes int id){
            return resources.getText(id);
        }
    }
}
