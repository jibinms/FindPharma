package wear.sunshine.android.example.com.capstone_1.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.activity.MedicineDetailsActivity;
import wear.sunshine.android.example.com.capstone_1.activity.PharmaciesActivity;
import wear.sunshine.android.example.com.capstone_1.response.Medicine;
import wear.sunshine.android.example.com.capstone_1.response.Pharmacy;

/**
 * Created by jibin on 21/11/16.
 */

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.ViewHolder> {

    public static final String INTENT_DATA = "data_";
    private Activity mActivity;
    private ArrayList mDataEntries = new ArrayList<>();
    private Random mRandom = new Random();

    private int colors[] = {R.color.list1, R.color.list2, R.color.list3, R.color.list4, R.color.list5, R.color.list6};

    public PharmacyAdapter(Activity activity, ArrayList<?> list) {
        mActivity = activity;
        mDataEntries = list;


    }

    @Override
    public PharmacyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacy_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PharmacyAdapter.ViewHolder holder, final int position) {

        holder.cardView.setBackgroundColor(ContextCompat.getColor(mActivity, colors[mRandom.nextInt(50) % 6]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MedicineDetailsActivity.class);
                if (mActivity instanceof PharmaciesActivity) {
                    intent.putExtra(INTENT_DATA, (Pharmacy) mDataEntries.get(position));
                } else {
                    intent.putExtra(INTENT_DATA, (Medicine) mDataEntries.get(position));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(mActivity, holder.productImageView, mActivity.getString(R.string.product_trans));
                    mActivity.startActivity(intent, options.toBundle());
                } else {
                    mActivity.startActivity(intent);
                }
            }
        });

        if (mActivity instanceof PharmaciesActivity) {
            Pharmacy pharmacy = (Pharmacy) mDataEntries.get(position);
            setPharmacyData(pharmacy, holder);
        } else {
            Medicine pharmacy = (Medicine) mDataEntries.get(position);
            setMedicineData(pharmacy, holder);

        }

    }

    private void setMedicineData(Medicine medicine, ViewHolder holder) {

        Glide.with(mActivity.getApplicationContext()).load(medicine.getImageUrl(holder.getAdapterPosition())).placeholder(android.R.drawable.ic_menu_crop).into(holder.productImageView);
        if (medicine.getTradeName() != null) {
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.titleTextView.setText(medicine.getTradeName());
        } else {
            holder.titleTextView.setVisibility(View.GONE);
        }
        if (medicine.getCompany() != null && medicine.getClosingTime() != null) {
            holder.timingTextView.setVisibility(View.VISIBLE);
            holder.timingTextView.setText(medicine.getCompany());
        } else {
            holder.timingTextView.setVisibility(View.GONE);
        }

        if (medicine.getLocalAgent() != null) {
            holder.addressTextView.setVisibility(View.VISIBLE);
            holder.addressTextView.setText(medicine.getLocalAgent());
        } else {
            holder.addressTextView.setVisibility(View.GONE);
        }

    }

    private void setPharmacyData(Pharmacy pharmacy, ViewHolder holder) {

        Glide.with(mActivity.getApplicationContext()).load(pharmacy.getImageUrl(holder.getAdapterPosition())).placeholder(android.R.drawable.ic_menu_crop).into(holder.productImageView);
        if (pharmacy.getName() != null) {
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.titleTextView.setText(pharmacy.getName());
        } else {
            holder.titleTextView.setVisibility(View.GONE);
        }
        if (pharmacy.getStartingTime() != null && pharmacy.getClosingTime() != null) {
            holder.timingTextView.setVisibility(View.VISIBLE);
            holder.timingTextView.setText(pharmacy.getStartingTime() + " - " + pharmacy.getClosingTime());
        } else {
            holder.timingTextView.setVisibility(View.GONE);
        }

        String address = "";

        if (!TextUtils.isEmpty(pharmacy.getStreet())) {
            address += pharmacy.getStreet() + "\n";
        }
        if (!TextUtils.isEmpty(pharmacy.getArea())) {
            address += pharmacy.getArea() + "\n";
        }
        if (!TextUtils.isEmpty(pharmacy.getRegion())) {
            address += pharmacy.getRegion() + "\n";
        }


        if (address != null) {
            holder.addressTextView.setVisibility(View.VISIBLE);
            holder.addressTextView.setText(address);
        } else {
            holder.addressTextView.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return mDataEntries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImageView;
        public TextView titleTextView;
        public TextView timingTextView;
        public TextView addressTextView;
        public LinearLayout cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            productImageView = (ImageView) itemView.findViewById(R.id.logo);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            timingTextView = (TextView) itemView.findViewById(R.id.timing);
            addressTextView = (TextView) itemView.findViewById(R.id.address);
            cardView = (LinearLayout) itemView.findViewById(R.id.card);
        }
    }

    public void addNewPharmacies(ArrayList<?> list) {
        mDataEntries.addAll(list);
        this.notifyDataSetChanged();
    }


}
