package id.web.wfebriadi.submission5.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ServiceFilmFavorite extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
