package io.prototype.package_tracker.app;

import io.prototype.package_tracker.log.Loggable;
import br.nullexcept.mux.app.Launch;
import br.nullexcept.mux.app.Looper;
import br.nullexcept.mux.view.View;
import br.nullexcept.mux.view.anim.RotationAnimation;
import br.nullexcept.mux.widget.LinearLayout;
import io.prototype.package_tracker.models.Order;
import io.prototype.package_tracker.models.OrderManager;
import io.prototype.package_tracker.widget.HomeTrack;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements Loggable {
    private RotationAnimation rotate;
    private int ro = -1;
    private int ri = 9999;
    private final Launch<TrackService> launchService = new Launch<>(TrackService.class);

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView("main_activity");

        findViewById("add_track").setOnClickListener(v-> startActivity(new Launch<>(CreateActivity.class)));
        listAll();
        refresh();
        postRefresh();
        rotate = new RotationAnimation(findViewById("reload_bar"), 750);
        rotate.setRotation(360,0);
    }

    @Override
    public void onResume() {
        super.onResume();
        oldLastChange = -1;
        refresh();
    }

    private void postRefresh() {
        Looper.getMainLooper().postDelayed(()->{
            if (isRunning()) {
                refresh();
                TrackService service = startService(launchService);
                if (service.isLoading()) {
                    if (ri >=  3) {
                        rotate.play();
                        ri = 0;
                    }
                    ri++;
                    findViewById("reload_bar").setAlpha(1.0f);
                    ro = 0;
                } else if (ro != 1) {
                    ro = 1;
                    ri = 999;
                    findViewById("reload_bar").setAlpha(0.0f);
                }
            }
            postRefresh();
        }, 250); //1 SECOND
    }

    private long oldLastChange = 0;
    private void refresh() {
        TrackService service = startService(new Launch<>(TrackService.class));
        if (oldLastChange != service.getLastChange()) {
            listAll();
            oldLastChange = service.getLastChange();
        }
    }

    public void listAll() {
        LinearLayout content = findViewById("contents");
        content.removeAllViews();
        ArrayList<Order> orders = new ArrayList<>(OrderManager.getAll());
        orders.sort((o1, o2) -> Long.compare(o2.getLastChanged(), o1.getLastChanged()));
        for (Order order: orders) {
            View item = new HomeTrack(this, order);
            item.setOnClickListener(v-> startActivity(new Launch<>(TrackActivity.class).put("package", order.getId())));
            content.addChild(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
