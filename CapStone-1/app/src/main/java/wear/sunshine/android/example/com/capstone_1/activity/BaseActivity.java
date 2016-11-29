package wear.sunshine.android.example.com.capstone_1.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import wear.sunshine.android.example.com.capstone_1.R;

/**
 * Created by jibin on 24/11/16.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;


    /**
     * Showing progress dialog
     */
    public void showProgressDialog() {

        try {
            dismissPendingProgressDialog();
            if (!(this).isFinishing()) {
                mProgressDialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage(getString(R.string.please_wait));
                mProgressDialog.setCancelable(false);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    /**
     * Dismissing pending progress dialog
     */
    public void dismissPendingProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }
}
