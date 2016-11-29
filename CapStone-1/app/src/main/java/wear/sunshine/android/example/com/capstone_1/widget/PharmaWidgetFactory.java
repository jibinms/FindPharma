package wear.sunshine.android.example.com.capstone_1.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import wear.sunshine.android.example.com.capstone_1.R;
import wear.sunshine.android.example.com.capstone_1.content.MedicineProvider;

/**
 * Created by jibin on 28/11/16.
 */

public class PharmaWidgetFactory  implements RemoteViewsService.RemoteViewsFactory {

    private Cursor mCursor;
    private Context mContext;
    private int mWidgetId;

    public PharmaWidgetFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        this.mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        mCursor = mContext.getContentResolver().query(MedicineProvider.CONTENT_URI,
                new String[]{MedicineProvider._ID, MedicineProvider.NAME, MedicineProvider.COMPANY,
                        MedicineProvider.LOCAL_AGENT, MedicineProvider.IMAGE_URL},
                        null,
                        null,
                null);
    }

    @Override
    public void onDataSetChanged() {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(MedicineProvider.CONTENT_URI,
                new String[]{MedicineProvider._ID, MedicineProvider.NAME, MedicineProvider.COMPANY,
                        MedicineProvider.LOCAL_AGENT, MedicineProvider.IMAGE_URL},
               null,null,
                null);
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor!=null ? mCursor.getCount():0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.pharmacy_list_item_widget);

        if (mCursor.moveToPosition(position)) {
            remoteViews.setTextViewText(R.id.title,  mCursor.getString(1));
            remoteViews.setTextViewText(R.id.address, mCursor.getString(2));
            remoteViews.setTextViewText(R.id.timing, mCursor.getString(3));

        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}