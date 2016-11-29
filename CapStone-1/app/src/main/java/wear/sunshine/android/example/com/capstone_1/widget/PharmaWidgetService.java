package wear.sunshine.android.example.com.capstone_1.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jibin on 28/11/16.
 */

public class PharmaWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new PharmaWidgetFactory(getApplicationContext(), intent);
    }
}