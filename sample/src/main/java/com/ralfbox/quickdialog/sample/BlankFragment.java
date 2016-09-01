package com.ralfbox.quickdialog.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ralfbox.quickdialog.QuickDialog;
import com.ralfbox.quickdialog.annotations.PositiveButtonQD;


public class BlankFragment extends Fragment {

    public static final String REQUEST_FIRST = "first-dialog";
    private static final String REQUEST_SECOND = "second-dialog";
    public static final String ARG_NAME = "name";

    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            name = args.containsKey(ARG_NAME) ? args.getString(ARG_NAME) : null;
        }

        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        v.findViewById(R.id.blankFragment_bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QuickDialog.Builder(BlankFragment.this, REQUEST_FIRST)
                        .title("Dialog 1")
                        .positiveButton("OK")
                        .show(getFragmentManager());
            }
        });

        v.findViewById(R.id.blankFragment_bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QuickDialog.Builder(BlankFragment.this, REQUEST_SECOND)
                        .title(R.string.secondDialog)
                        .message(R.string.clickOKToInvokeMehtodOnSecondDialogClick)
                        .positiveButton(R.string.ok)
                        .negativeButton(R.string.cancel)
                        .cancelable(false)
                        .show(getFragmentManager());
            }
        });
        return v;
    }


    @PositiveButtonQD(REQUEST_FIRST)
    private void onFirstDialogClick(QuickDialog quickDialog){
        Toast.makeText(getActivity(), getString(R.string.clickedFirstDialog, name) + "\nQuickDialog: " + quickDialog.toString(), Toast.LENGTH_LONG).show();
    }

    @PositiveButtonQD(REQUEST_SECOND)
    public void onSecondDialogClick(){
        Toast.makeText(getActivity(), getString(R.string.clickedSecondDialog, name), Toast.LENGTH_LONG).show();
    }

}
