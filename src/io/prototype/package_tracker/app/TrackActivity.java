package io.prototype.package_tracker.app;

import br.nullexcept.apis.tracker.tracking.Tracker;
import br.nullexcept.mux.app.applets.ClipboardApplet;
import br.nullexcept.mux.view.View;
import br.nullexcept.mux.widget.LinearLayout;
import br.nullexcept.mux.widget.TextView;
import io.prototype.package_tracker.models.Order;
import io.prototype.package_tracker.models.OrderManager;
import io.prototype.package_tracker.widget.InnerTrack;

import java.util.ArrayList;

public class TrackActivity extends BaseActivity {
    private Order order;

    @Override
    public void onCreate() {
        super.onCreate();
        order = OrderManager.get(getParcel().getString("package"));
        setContentView("track_activity");
        ((TextView)findViewById("header_title")).setText(order.getLabel());
        findViewById("back").setOnClickListener(v-> finish());
        ((TextView)findViewById("header_track")).setText(order.getCode());
        findViewById("header_track").setOnClickListener(v-> ((ClipboardApplet)getApplet(CLIPBOARD_APPLET)).setContent(((TextView)v).getText()+""));

        findViewById("delete").setOnClickListener( v -> {
            OrderManager.remove(order);
            finish();
        });

        ArrayList<Tracker> tracks = new ArrayList<>(order.getTracks());
        LinearLayout content = findViewById("content");

        for (int i = tracks.size()-1; i >= 0; i--) {
            View child = new InnerTrack(this, order, tracks.get(i));
            if (i == tracks.size() - 1) {
                child.findViewById("line_up").setVisibility(View.VISIBILITY_HIDDEN);
            }
            if (i == 0) {
                child.findViewById("line_down").setVisibility(View.VISIBILITY_HIDDEN);
            }
            content.addChild(child);
        }
    }
}
