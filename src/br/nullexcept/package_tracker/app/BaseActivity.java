package br.nullexcept.package_tracker.app;

import br.nullexcept.package_tracker.models.Config;
import br.nullexcept.mux.app.Activity;
import br.nullexcept.mux.app.Looper;
import br.nullexcept.mux.view.View;

public class BaseActivity extends Activity {
    private boolean running = true;
    protected <T extends View> T find(String tag) {
        return getWindow().getContentView().findViewByTag(tag);
    }


    @Override
    protected void post(Runnable runnable) {
        super.post(runnable);
    }

    protected void runDelayed(Runnable runnable, int time) {
        Looper.getMainLooper().postDelayed(()->{
            if (isRunning()) {
                runnable.run();
            }
        }, time);
    }

    @Override
    public void onResume() {
        super.onResume();
        running = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Config.write();
        running = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Config.write();
        running = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setTitle("PACKAGE TRACKER");
        running = true;
    }

    public boolean isRunning() {
        return running;
    }
}
