package wear.sunshine.android.example.com.capstone_1.activity;

import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
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
import wear.sunshine.android.example.com.capstone_1.content.MedicineProvider;
import wear.sunshine.android.example.com.capstone_1.response.Medicine;
import wear.sunshine.android.example.com.capstone_1.response.Pharmacy;
import wear.sunshine.android.example.com.capstone_1.utilities.Utility;

public class MedicineActivity extends ListBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharamacies);
        initViews();
        loadMedicines();
    }

    /**
     *Loading all medicine
     */
    private void loadMedicines() {

        if  (FindPharma.isNetworkAvailable()){
            showProgressDialog();
            FindPharma.sFindPharmaWebservice.getMedicine(0).enqueue(new Callback<List<Medicine>>() {
                @Override
                public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                    dismissPendingProgressDialog();
                    if (response != null) {
                        if (response.body() != null) {

                            mErrorText.setVisibility(View.GONE);
                            mPharmacyAdapter.addNewPharmacies((ArrayList<?>) response.body());
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mPharmacyAdapter.notifyDataSetChanged();

                            try {
                                getContentResolver().delete(MedicineProvider.CONTENT_URI,null,null);
                                getContentResolver().applyBatch("wear.sunshine.android.example.com.capstone_1.content.MedicineProvider",
                                        Utility.buildBatchOperation((ArrayList<Medicine>) response.body()));
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            } catch (OperationApplicationException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    }
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<List<Medicine>> call, Throwable t) {
                    dismissPendingProgressDialog();
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }
            });
        }else{
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_net) , Snackbar.LENGTH_SHORT).show();
        }
    }
    /**
     * Search medicine api call
     */
    private void loadMedicine(String text) {
        //showProgressDialog();
        if  (FindPharma.isNetworkAvailable()){
            FindPharma.sFindPharmaWebservice.searchMedicineName(text).enqueue(new Callback<List<Medicine>>() {
                @Override
                public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                    dismissPendingProgressDialog();
                    if (response != null) {
                        if (response.body() != null) {

                            mErrorText.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mPharmacyAdapter = new PharmacyAdapter(MedicineActivity.this, (ArrayList<?>) response.body());
                            mRecyclerView.setAdapter(mPharmacyAdapter);
                            return;
                        }
                    }
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<List<Medicine>> call, Throwable t) {
                    dismissPendingProgressDialog();
                    mRecyclerView.setVisibility(View.GONE);
                    mErrorText.setVisibility(View.VISIBLE);
                }
            });
        }else{
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_net) , Snackbar.LENGTH_SHORT).show();
        }
    }


    public void reloadPharmacies(View v){
        mPharmacyAdapter = new PharmacyAdapter(this,new ArrayList<Pharmacy>());
        mRecyclerView.setAdapter(mPharmacyAdapter);
        loadMedicines();
    }


    /**
     * Text change listener
     */
    @Override
    public void searchDataChanged(String s) {

        if(s.length()>2){
            loadMedicine(s);
        }else if (s.length()==0){
            mPharmacyAdapter = new PharmacyAdapter(this,new ArrayList<Pharmacy>());
            mRecyclerView.setAdapter(mPharmacyAdapter);
            loadMedicines();
        }
    }
}
