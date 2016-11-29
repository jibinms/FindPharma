package wear.sunshine.android.example.com.capstone_1.utilities;

import android.content.ContentProviderOperation;


import java.util.ArrayList;

import wear.sunshine.android.example.com.capstone_1.content.MedicineProvider;
import wear.sunshine.android.example.com.capstone_1.response.Medicine;

/**
 * Created by jibin on 28/11/16.
 */

public class Utility {

    public static  ArrayList<ContentProviderOperation> buildBatchOperation(ArrayList<Medicine> medicines){

        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();

        for (Medicine medicine:medicines ) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                    MedicineProvider.CONTENT_URI);
            builder.withValue(MedicineProvider.NAME, medicine.getTradeName());
            builder.withValue(MedicineProvider.COMPANY, medicine.getCompany());
            builder.withValue(MedicineProvider.LOCAL_AGENT, medicine.getLocalAgent());
            builder.withValue(MedicineProvider.IMAGE_URL, medicine.imageUrl);
            batchOperations.add(builder.build());
        }
        return batchOperations;
    }
}
