package io.prototype.package_tracker.widget;

import io.prototype.apis.tracker.tracking.Tracker;
import br.nullexcept.mux.app.Context;
import br.nullexcept.mux.view.View;
import br.nullexcept.mux.view.ViewGroup;
import br.nullexcept.mux.widget.ImageView;
import br.nullexcept.mux.widget.LinearLayout;
import br.nullexcept.mux.widget.TextView;
import io.prototype.package_tracker.models.Order;
import io.prototype.package_tracker.shipper.TrackRes;

public class InnerTrack extends LinearLayout {
    public InnerTrack(Context context, Order order, Tracker tracker) {
        super(context);
        View inner = context.getLayoutInflater().inflate("small_track_item");
        inner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addChild(inner);

        ImageView icon = findViewById("icon");
        TextView title = findViewById("title");
        TextView desc = findViewById("desc");
        TextView time = findViewById("time");

        String[] date = tracker.getDate().split("/");

        desc.setText(tracker.isRiding() ? tracker.getFrom() + " "+context.getString("to")+" " + tracker.getTo() : tracker.getFrom());
        title.setText(tracker.getStatus());
        time.setText(tracker.getHour()+"\n"+date[0]+"/"+date[1]);
        icon.setImageDrawable(TrackRes.getIcon(order, tracker));

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
