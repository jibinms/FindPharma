package wear.sunshine.android.example.com.capstone_1.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import wear.sunshine.android.example.com.capstone_1.R;

public class NotificationListActivity extends AppCompatActivity {

    public static final String DATA = "data_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharamacies);

        if (getIntent().hasExtra(DATA)) {

            TextView textView = (TextView) findViewById(R.id.errorText);
            textView.setText(getIntent().getStringExtra(DATA));
        }

    }

}
