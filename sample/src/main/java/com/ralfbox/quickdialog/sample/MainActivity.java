package com.ralfbox.quickdialog.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ralfbox.quickdialog.ControllerQD;
import com.ralfbox.quickdialog.QuickDialog;
import com.ralfbox.quickdialog.NegativeButtonQD;
import com.ralfbox.quickdialog.PositiveButtonQD;

public class MainActivity extends AppCompatActivity {

    private static final String REQUEST_QD_ACTIVITY_DIALOG = "activity-dialog-app";
    private static final String REQUEST_QD_CONTROLLER_SAMPLE = "sample-controller";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            Bundle b = new Bundle();
            b.putString(BlankFragment.ARG_NAME, "Fragment 2");
            Fragment f = new BlankFragment();
            f.setArguments(b);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, f)
                    .commit();

        }
    }

    public void onShowAlertClick(View view) {
        new QuickDialog.Builder(this, REQUEST_QD_ACTIVITY_DIALOG)
                .title(R.string.activityDialog)
                .message(R.string.callToActivity)
                .positiveButton(R.string.ok)
                .negativeButton(R.string.cancel)
                .show(getSupportFragmentManager());
    }


    @PositiveButtonQD(REQUEST_QD_ACTIVITY_DIALOG)
    private void onPositiveButtonClick(int which){
        Toast.makeText(this, getString(R.string.clickedOK) + "  button.which: " + which, Toast.LENGTH_SHORT).show();
    }

    @NegativeButtonQD(REQUEST_QD_ACTIVITY_DIALOG)
    public void onNegativeButtonAnyNameMethode(){
        Toast.makeText(this, R.string.clickedCancel, Toast.LENGTH_SHORT).show();
    }


/** QuickDialog with ControllerQD */
    public void onCreateQDWithController(View view) {
        new QuickDialog.Builder(this, REQUEST_QD_CONTROLLER_SAMPLE)
                .title("Enter any text")
                .positiveButton(R.string.ok)
                .negativeButton(R.string.cancel)
                .controller(ControllerSample.class)
                .cancelable(false)
                .show(getSupportFragmentManager());
    }

    @PositiveButtonQD(REQUEST_QD_CONTROLLER_SAMPLE)
    private void enteredText(ControllerSample controller) {
        Toast.makeText(this, "Entered text: " + controller.textView.getText(), Toast.LENGTH_LONG).show();
    }


    public static class ControllerSample extends ControllerQD{
        private TextView textView;
        private QuickDialog quickDialog;

        @Override
        public void onCreate(QuickDialog quickDialog, Bundle savedInstanceState) {
            this.quickDialog = quickDialog;
        }

        @Override
        public AlertDialog.Builder onCreateBuilder(AlertDialog.Builder builder) {
            View v = View.inflate(quickDialog.getActivity(), R.layout.view_alert, null);
            textView = (TextView) v.findViewById(R.id.textView);
            builder.setView(v);
            return builder;
        }
    }
}
