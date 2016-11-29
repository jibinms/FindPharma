package wear.sunshine.android.example.com.capstone_1.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import wear.sunshine.android.example.com.capstone_1.R;

/**
 * Created by jibin on 25/11/16.
 */

public class AdvertViewPagerAdapter  extends PagerAdapter {

    private Context mContext;
    private String[] mImagesBackground;
    private LayoutInflater mInflater;

    public AdvertViewPagerAdapter(Context context,  String[] viewPagerImagesBackground) {
        mContext = context;
        this.mImagesBackground =viewPagerImagesBackground;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        mInflater = (LayoutInflater) mContext
                .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewLayout = (ViewGroup) mInflater.inflate(
                R.layout.viewpager_item, collection, false);

        int pos = position % mImagesBackground.length;
        ImageView pagerLayout = (ImageView) viewLayout.findViewById(R.id.adImage);
        Glide.with(mContext.getApplicationContext()).load(mImagesBackground[pos]).placeholder(android.R.drawable.ic_menu_crop).into(pagerLayout);

        collection.addView(viewLayout, 0);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((ViewGroup) view);

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;

    }


}