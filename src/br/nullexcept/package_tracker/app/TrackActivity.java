package br.nullexcept.package_tracker.app;

import br.nullexcept.apis.tracker.tracking.Tracker;
import br.nullexcept.mux.app.applets.ClipboardApplet;
import br.nullexcept.mux.view.View;
import br.nullexcept.mux.widget.ImageView;
import br.nullexcept.mux.widget.LinearLayout;
import br.nullexcept.mux.widget.TextView;
import br.nullexcept.package_tracker.models.Order;
import br.nullexcept.package_tracker.models.OrderManager;
import br.nullexcept.package_tracker.shipper.TrackRes;

import java.util.ArrayList;

public class TrackActivity extends BaseActivity {
    private Order order;

    @Override
    public void onCreate() {
        super.onCreate();
        order = OrderManager.get(getParcel().getString("package"));
        setContentView("track_activity");
        ((TextView)find("header_title")).setText(order.getLabel());
        find("back").setOnClickListener(v-> finish());
        ((TextView)find("header_track")).setText(order.getCode());
        find("header_track").setOnClickListener(v-> ((ClipboardApplet)getApplet(CLIPBOARD_APPLET)).setContent(((TextView)v).getText()+""));
        find("delete").setOnClickListener( v -> {
            OrderManager.remove(order);
            finish();
        });

        ArrayList<Tracker> tracks = new ArrayList<>(order.getTracks());
        LinearLayout content = find("content");

        for (int i = tracks.size()-1; i >= 0; i--) {
            Tracker track = tracks.get(i);
            View item = getLayoutInflater().inflate("small_track_item");
            String[] date = track.getDate().split("/");
            ((TextView)item.findViewByTag("time"))
                    .setText(track.getHour()+"\n"+date[0]+"/"+date[1]);
            ((TextView)item.findViewByTag("title"))
                    .setText(track.getStatus());

            if (i == 0) {
                View line = item.findViewById("line_down");
                line.getParent().removeChild(line);
            }

            if (i == tracks.size()-1) {
                View line = item.findViewById("line_up");
                line.getParent().removeChild(line);
            }

            String desc = track.getFrom();
            if (track.isRiding()) {
                desc += " PARA "+track.getTo();
            }

            ((TextView)item.findViewByTag("desc")).setText(desc);
            ((ImageView)item.findViewById("icon")).setImageDrawable(TrackRes.getIcon(order, track));
            content.addChild(item);
        }
    }
}
