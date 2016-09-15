package com.ralfbox.quickdialog.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ralfbox.quickdialog.NegativeButtonQD;
import com.ralfbox.quickdialog.QuickDialog;
import com.ralfbox.quickdialog.PositiveButtonQD;


public class BlankFragment extends Fragment {

    public static final String REQUEST_FIRST = "first-dialog";
    private static final String REQUEST_SECOND = "second-dialog";
    public static final String ARG_NAME = "name";
    private static final String ARG_COUNTERS = "counters";

    private String name;
    private int counterPositiveBT1 = 0;
    private int counterNegativeBT1 = 0;
    private int counterPositiveBT2 = 0;
    private int counterNegativeBT2 = 0;

    private Button bt1;
    private Button bt2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            int[] counters = savedInstanceState.getIntArray(ARG_COUNTERS);
            if (counters != null && counters.length >= 4) {
                counterPositiveBT1 = counters[0];
                counterNegativeBT1 = counters[1];
                counterPositiveBT2 = counters[2];
                counterNegativeBT2 = counters[3];
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            name = args.containsKey(ARG_NAME) ? args.getString(ARG_NAME) : null;
        }

        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        (bt1 = (Button)v.findViewById(R.id.blankFragment_bt1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QuickDialog.Builder(BlankFragment.this, REQUEST_FIRST)
                        .title("Dialog 1")
                        .positiveButton("Positive")
                        .negativeButton("Negative")
                        .show(getFragmentManager());
            }
        });

        (bt2 = (Button) v.findViewById(R.id.blankFragment_bt2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QuickDialog.Builder(BlankFragment.this, REQUEST_SECOND)
                        .title(R.string.secondDialog)
                        .message(R.string.clickOKToInvokeMehtodOnSecondDialogClick)
                        .positiveButton(R.string.ok)
                        .negativeButton(R.string.cancel)
                        .neutralButton("Close App")
                        .finishActivityIfNeutralBTClicked()
                        .cancelable(false)
                        .show(getFragmentManager());
            }
        });
        reloadTextButtons();
        return v;
    }

    private void reloadTextButtons(){
        bt1.setText(getString(R.string.coutersButtonText, counterPositiveBT1, counterNegativeBT1));
        bt2.setText(getString(R.string.coutersButtonText, counterPositiveBT2, counterNegativeBT2));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(ARG_COUNTERS, new int[]{counterPositiveBT1, counterNegativeBT1, counterPositiveBT2, counterNegativeBT2 });
    }

/** Button 1 */
    @PositiveButtonQD(REQUEST_FIRST)
    private void onFirstDialogClick(QuickDialog quickDialog){ // metode may get QuickDialog
        counterPositiveBT1++;
        reloadTextButtons();
    }

    @NegativeButtonQD(REQUEST_FIRST)
    private void onNegativeQD1Click(){
        counterNegativeBT1++;
        reloadTextButtons();
    }

/** Button 2*/
    @PositiveButtonQD(REQUEST_SECOND)
    @NegativeButtonQD(REQUEST_SECOND)
    public void onSecondDialogClick(int which){
        if (which == DialogInterface.BUTTON_POSITIVE) counterPositiveBT2++;
        else if (which == DialogInterface.BUTTON_NEGATIVE) counterNegativeBT2++;
        reloadTextButtons();
    }


}
