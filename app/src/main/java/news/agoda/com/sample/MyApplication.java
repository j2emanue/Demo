package news.agoda.com.sample;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by jeffery.emanuel on 2017-02-25.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
