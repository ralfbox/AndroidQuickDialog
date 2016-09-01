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

import com.ralfbox.quickdialog.core.CallResultQuickDialogEngineer;

/**
 * @author Rafal Pudelko
 *         Created by Admin on 30.08.2016.
 */
public class QuickDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    private static final String ARG_CANCELLABLE = "cancellable";
    private static final String ARG_POSITIVE_BUTTON = "bt-positive";
    private static final String ARG_NEGATIVE_BUTTON = "bt-negative";
    private static final String ARG_NEUTRAL_BUTTON = "bt-neutral";
    private static final String ARG_STYLE = "style";

    private static final String ARG_RESULT_TO_ACTIVITY = "resutl-to-activity";
    private static final String ARG_FRAGMENT_ID = "fragment-id";
    private static final String ARG_FRAGMENT_TAG = "fragment-tag";

    private static final String ARG_REQUEST_TAG = "tag-quick-alert";

    private String requestTag;
    private boolean resultToActivity;
    private String resultFragmentTag;
    private int resultFragmentId;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        init(args);

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

        return ret;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        new CallResultQuickDialogEngineer(this, which).execute();
    }

    public Object getBaseObjectToCallResult() {
        if (isResultToActivity()) return getActivity();
        if (resultFragmentTag != null)
            return getFragmentManager().findFragmentByTag(resultFragmentTag);
        return getFragmentManager().findFragmentById(resultFragmentId);
    }

    public String getRequestTag() {
        return requestTag;
    }

    public boolean isResultToActivity() {
        return resultToActivity;
    }

    public String getResultFragmentTag() {
        return resultFragmentTag;
    }

    public int getResultFragmentId() {
        return resultFragmentId;
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

        public Builder cancelable(boolean cancelable) {
            bundle.putBoolean(ARG_CANCELLABLE, cancelable);
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
