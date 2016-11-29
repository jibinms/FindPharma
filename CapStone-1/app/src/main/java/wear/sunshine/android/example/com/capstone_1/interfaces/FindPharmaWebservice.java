package wear.sunshine.android.example.com.capstone_1.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import wear.sunshine.android.example.com.capstone_1.response.Medicine;
import wear.sunshine.android.example.com.capstone_1.response.Pharmacy;
import wear.sunshine.android.example.com.capstone_1.response.TokenSuccess;

/**
 * Created by jibin on 23/11/16.
 */

public interface FindPharmaWebservice {

    @POST("Medicines/GetAllMedicine")
    Call<List<Medicine>> getMedicine(@Query("offset") int offset);

    //findout a medicine
    @POST("Medicines/SearchMedicineByTradeName")
    Call<List<Medicine>> searchMedicineName(@Query("SearchString") String searchString);

    @POST("Pharmacy/GetAllPharmacy")
    Call<List<Pharmacy>> getPharmacies(@Query("offset") int offset);

    @POST("Pharmacy/SearchPharmacyName")
    Call<List<Pharmacy>> searchPharmacy(@Query("SearchString") String searchString);

    @POST("Pharmacy/sendFCM")
    Call<TokenSuccess> sendFCMToken(@Query("SearchString") String searchString);
}
