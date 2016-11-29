package wear.sunshine.android.example.com.capstone_1.FCM;

import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wear.sunshine.android.example.com.capstone_1.activity.MedicineActivity;
import wear.sunshine.android.example.com.capstone_1.adapter.PharmacyAdapter;
import wear.sunshine.android.example.com.capstone_1.app.FindPharma;
import wear.sunshine.android.example.com.capstone_1.response.Medicine;
import wear.sunshine.android.example.com.capstone_1.response.TokenSuccess;

/**
 * Created by jibin on 25/11/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String token) {
        if  (FindPharma.isNetworkAvailable()){
            FindPharma.sFindPharmaWebservice.sendFCMToken(token).enqueue(new Callback<TokenSuccess>() {
                @Override
                public void onResponse(Call<TokenSuccess> call, Response<TokenSuccess> response) {


                }
                @Override
                public void onFailure(Call<TokenSuccess> call, Throwable t) {

                }
            });
        }
    }
}