package wear.sunshine.android.example.com.capstone_1.activity;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.adapter.PharmacyAdapter;
import wear.sunshine.android.example.com.capstone_1.response.Pharmacy;

/**
 * Created by jibin on 23/11/16.
 */

public abstract class ListBaseActivity extends BaseActivity {

    public View mErrorText;
    public PharmacyAdapter mPharmacyAdapter;
    public RecyclerView mRecyclerView;

    public abstract void searchDataChanged(String s);

    public void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.pharmacy);
        mErrorText = findViewById(R.id.errorText);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPharmacyAdapter = new PharmacyAdapter(this, new ArrayList<Pharmacy>());
        mRecyclerView.setAdapter(mPharmacyAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchDataChanged(s);

                return false;
            }
        });

        return true;

    }
}
