package br.nullexcept.package_tracker.app;

import br.nullexcept.package_tracker.models.Config;
import br.nullexcept.package_tracker.shipper.TrackRes;
import br.nullexcept.mux.app.Launch;

public class LaunchActivity extends BaseActivity {
    @Override
    public void onCreate() {
        super.onCreate();
        setContentView("launch_screen");
        setTitle("PACKAGE TRACKER");
        postDelayed(()->{
            Config.init(this);
            TrackRes.load(this);
            startService(new Launch<>(TrackService.class));
            startActivity(new Launch<>(MainActivity.class));
            finish();
        },200);
    }
}
