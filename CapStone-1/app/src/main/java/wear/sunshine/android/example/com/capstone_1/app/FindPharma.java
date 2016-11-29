package wear.sunshine.android.example.com.capstone_1.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.util.Random;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wear.sunshine.android.example.com.capstone_1.BuildConfig;
import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.interfaces.FindPharmaWebservice;

/**
 * Created by jibin on 23/11/16.
 */

public class FindPharma extends Application {

    private static FindPharma sFindPharma;
    public static FindPharmaWebservice sFindPharmaWebservice = new Retrofit.Builder()
            .baseUrl(BuildConfig.ServerUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FindPharmaWebservice.class);

    @Override
    public void onCreate() {
        super.onCreate();
        sFindPharma= this;
    }

    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) sFindPharma.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        return netInfo !=null && netInfo.isConnected();

    }

    public static String imgUrl(boolean isPharma,int pos){
        return isPharma ? sFindPharma.getResources().getStringArray(R.array.pharmaciesImage)[pos%6]: sFindPharma.getResources().getStringArray(R.array.medicineImage)[pos%6];
    }
}
