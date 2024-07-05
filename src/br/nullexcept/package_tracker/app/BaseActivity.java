package br.nullexcept.package_tracker.app;

import br.nullexcept.mux.app.Activity;
import br.nullexcept.package_tracker.models.Config;

public class BaseActivity extends Activity {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Config.write();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Config.write();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setTitle("PACKAGE TRACKER");
    }
}
