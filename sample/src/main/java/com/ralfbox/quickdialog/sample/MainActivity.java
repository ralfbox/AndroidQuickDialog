package com.ralfbox.quickdialog.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ralfbox.quickdialog.QuickDialog;
import com.ralfbox.quickdialog.NegativeButtonQD;
import com.ralfbox.quickdialog.PositiveButtonQD;

public class MainActivity extends AppCompatActivity {

    public static final String REQUEST_ACTIVITY_DIALOG = "activity-dialog-app";

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
        new QuickDialog.Builder(this, REQUEST_ACTIVITY_DIALOG)
                .title(R.string.activityDialog)
                .message(R.string.callToActivity)
                .positiveButton(R.string.ok)
                .negativeButton(R.string.cancel)
                .show(getSupportFragmentManager());
    }


    @PositiveButtonQD(REQUEST_ACTIVITY_DIALOG)
    private void onPositiveButtonClick(int which){
        Toast.makeText(this, getString(R.string.clickedOK) + "  button.which: " + which, Toast.LENGTH_SHORT).show();
    }

    @NegativeButtonQD(REQUEST_ACTIVITY_DIALOG)
    public void onNegativeButtonAnyNameMethode(){
        Toast.makeText(this, R.string.clickedCancel, Toast.LENGTH_SHORT).show();
    }
}
