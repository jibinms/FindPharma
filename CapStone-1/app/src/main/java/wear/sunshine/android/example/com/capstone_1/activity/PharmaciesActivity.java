package wear.sunshine.android.example.com.capstone_1.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.adapter.PharmacyAdapter;
import wear.sunshine.android.example.com.capstone_1.app.FindPharma;
import wear.sunshine.android.example.com.capstone_1.response.Pharmacy;

public class PharmaciesActivity extends ListBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharamacies);
        initViews();
        loadPharmacy();
    }


    private void loadPharmacy() {

        if (FindPharma.isNetworkAvailable()) {
            showProgressDialog();
            FindPharma.sFindPharmaWebservice.getPharmacies(0).enqueue(new Callback<List<Pharmacy>>() {
                @Override
                public void onResponse(Call<List<Pharmacy>> call, Response<List<Pharmacy>> response) {
                    dismissPendingProgressDialog();
                    if (response != null) {
                        if (response.body() != null) {

                            mErrorText.setVisibility(View.GONE);
                            mPharmacyAdapter.addNewPharmacies((ArrayList<?>) response.body());
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mPharmacyAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<List<Pharmacy>> call, Throwable t) {
                    dismissPendingProgressDialog();
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_net), Snackbar.LENGTH_SHORT).show();
        }
    }


    private void loadPharmacy(String text) {
        //showProgressDialog();
        if (FindPharma.isNetworkAvailable()) {
            FindPharma.sFindPharmaWebservice.searchPharmacy(text).enqueue(new Callback<List<Pharmacy>>() {
                @Override
                public void onResponse(Call<List<Pharmacy>> call, Response<List<Pharmacy>> response) {
                    dismissPendingProgressDialog();
                    if (response != null) {
                        if (response.body() != null) {

                            mErrorText.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mPharmacyAdapter = new PharmacyAdapter(PharmaciesActivity.this, (ArrayList<?>) response.body());
                            mRecyclerView.setAdapter(mPharmacyAdapter);
                            return;
                        }
                    }
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<List<Pharmacy>> call, Throwable t) {
                    dismissPendingProgressDialog();
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_net), Snackbar.LENGTH_SHORT).show();
        }
    }


    public void reloadPharmacies(View v) {
        mPharmacyAdapter = new PharmacyAdapter(this, new ArrayList<Pharmacy>());
        mRecyclerView.setAdapter(mPharmacyAdapter);
        loadPharmacy();
    }

    @Override
    public void searchDataChanged(String s) {

        if (s.length() > 2) {
            loadPharmacy(s);
        } else if (s.length() == 0) {
            mPharmacyAdapter = new PharmacyAdapter(this, new ArrayList<Pharmacy>());
            mRecyclerView.setAdapter(mPharmacyAdapter);
            loadPharmacy();
        }

    }
}
