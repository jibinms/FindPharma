package wear.sunshine.android.example.com.capstone_1.activity;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;

import com.google.firebase.iid.FirebaseInstanceId;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.adapter.AdvertViewPagerAdapter;
import wear.sunshine.android.example.com.capstone_1.app.FindPharma;
import wear.sunshine.android.example.com.capstone_1.content.MedicineProvider;
import wear.sunshine.android.example.com.capstone_1.response.TokenSuccess;
import wear.sunshine.android.example.com.capstone_1.utilities.CircularPagerIndicator;
import wear.sunshine.android.example.com.capstone_1.utilities.FixedSpeedScroller;
import wear.sunshine.android.example.com.capstone_1.utilities.GestureCalculator;
import wear.sunshine.android.example.com.capstone_1.widget.PharmaWidget;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor>{


    private static final int sVIEWPAGER_CHANGE_INTERVAL = 4000;
    private static final int sVIEWPAGER_START_DELAY = 100;

    private static final String sSCROLLER_FIELD_NAME = "mScroller";
    private static final String sINTERPOLATOR_FIELD_NAME = "sInterpolator";
    private Timer mViewPagerTimer;
    private AdvertViewPagerAdapter mViewPagerAdapter;
    private Runnable mViewPagerRunnable;
    private Handler mHandler=new Handler();
    private ViewPager mViewPager;
    private String[] mViewPagerImages;
    private GestureDetector mGestureDetector;
    private int mSelectedPage;
    private CircularPagerIndicator mViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mViewPager = (ViewPager)findViewById(R.id.advertPager);
        mViewPagerIndicator =(CircularPagerIndicator)findViewById(R.id.pagerIndic);
        setClickListeners();
        setUpViewPager();
        setScrollerInViewpager();
        sendRegistrationTokenToServer(FirebaseInstanceId.getInstance().getToken());

    }

    /**
     * Registering click listeners
     */
    private void setClickListeners() {
        findViewById(R.id.medicine).setOnClickListener(this);
        findViewById(R.id.pharmacy).setOnClickListener(this);
        setOnClickListeners();
    }

    @Override
    public void onClick(View view) {

        Intent intent = null;
        if (view.getId() == R.id.medicine){
            intent = new Intent(HomeActivity.this,MedicineActivity.class);
        }else {
            intent = new Intent(HomeActivity.this,PharmaciesActivity.class);
        }

        if (intent !=null){
            startActivity(intent);
        }
    }

    /**
     * Setting scroll for view pager
     */
    private void setScrollerInViewpager() {
        try {
            Field mScroller = ViewPager.class
                    .getDeclaredField(sSCROLLER_FIELD_NAME);
            Field interpolator = ViewPager.class
                    .getDeclaredField(sINTERPOLATOR_FIELD_NAME);
            interpolator.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(
                    mViewPager.getContext(),
                    (Interpolator) interpolator.get(null));
            mScroller.setAccessible(true);
            interpolator.setAccessible(true);
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {

            e.printStackTrace();
        }
    }
    /**
     * Declaring adapter
     */
    private void setUpViewPager() {
        if (mViewPagerRunnable == null) {
            initViewPagerRunnable();
        }
        if (mViewPagerAdapter == null) {
            mViewPagerImages =  getResources().getStringArray(R.array.pharmaciesImage);
            mSelectedPage = mViewPagerImages.length * 100;
            mViewPagerAdapter = new AdvertViewPagerAdapter(this,mViewPagerImages);
            mViewPager.setAdapter(mViewPagerAdapter);
            mViewPagerIndicator.setmCount(mViewPagerImages.length);
            autoChangeViewPager(0);
        } else {
            autoChangeViewPager(0);
        }
    }

    /**
     * Scheduling timer for changing viewpager page
     */
    private void autoChangeViewPager(int delay) {
        mHandler.removeCallbacks(mViewPagerRunnable);
        mViewPagerTimer = new Timer();
        mViewPagerTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                mHandler.post(mViewPagerRunnable);
            }
        }, delay, sVIEWPAGER_CHANGE_INTERVAL);
    }


    private void setOnClickListeners() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int page) {

                mViewPagerIndicator.pageChanged(page % mViewPagerImages.length);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureCalculator() {

            @Override
            public void onSwipeRightToLeft() {
                callBackTimer();
                autoChangeViewPager(sVIEWPAGER_CHANGE_INTERVAL);
            }

            @Override
            public void onSwipeLeftToRight() {
                callBackTimer();
                autoChangeViewPager(sVIEWPAGER_CHANGE_INTERVAL);
            }
        });
    }

    /**
     * Canceling running timer
     */
    private void callBackTimer() {
        if (mViewPagerTimer != null) {
            mViewPagerTimer.cancel();
            mViewPagerTimer.purge();
            mViewPagerTimer = null;
        }
    }

    @Override
    protected void onPause() {
        callBackTimer();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        setUpViewPager();
    }
    /**
     * set upping auto change runner
     */
    private void initViewPagerRunnable() {
        mViewPagerRunnable = new Runnable() {
            @Override
            public void run() {
                if (mViewPager != null && mViewPager.getAdapter() != null) {

                    int position = mViewPager.getCurrentItem();
                    if (position < 1) {
                        position = mViewPagerImages.length * 100;
                        position = position - 1;
                    }

                    mSelectedPage = position + 1;
                    try {
                        if (mSelectedPage > mViewPager.getAdapter()
                                .getCount() - 1) {
                            mSelectedPage = (mViewPagerImages.length * 10);
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    mViewPager.setCurrentItem(mSelectedPage, true);

                }
            }
        };
    }

    /**
     * Sending fcm token to server
     */
    private void sendRegistrationTokenToServer(String token) {
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


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(this, MedicineProvider.CONTENT_URI,
                new String[]{ MedicineProvider._ID, MedicineProvider.NAME, MedicineProvider.LOCAL_AGENT,
                        MedicineProvider.COMPANY, MedicineProvider.IMAGE_URL},null, null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){

        updateStocksWidget();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){

    }

    /**
     * Updating widget
     */
    private void updateStocksWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(this, PharmaWidget.class));
        if(ids.length > 0) {
            appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.medicines);
        }
    }
}
