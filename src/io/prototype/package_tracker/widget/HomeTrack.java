package io.prototype.package_tracker.widget;

import br.nullexcept.apis.tracker.tracking.Tracker;
import br.nullexcept.mux.app.Context;
import br.nullexcept.mux.view.View;
import br.nullexcept.mux.view.ViewGroup;
import br.nullexcept.mux.widget.ImageView;
import br.nullexcept.mux.widget.LinearLayout;
import br.nullexcept.mux.widget.TextView;
import io.prototype.package_tracker.models.Order;
import io.prototype.package_tracker.shipper.TrackRes;

public class HomeTrack extends LinearLayout {
    public HomeTrack(Context context, Order order) {
        super(context);
        View base = context.getLayoutInflater().inflate("track_item");
        addChild(base, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = findViewById("title");
        TextView status = findViewById("status");
        TextView desc = findViewById("desc");
        ImageView icon = findViewById("icon");

        Tracker lastTrack = new Tracker("","", context.getString("no_data"), "00/00/00 00:00");;
        if (order.getTrackCount() > 0) {
            lastTrack = order.getTracks().get(order.getTrackCount()-1);
        }

        icon.setImageDrawable(TrackRes.getIcon(order, lastTrack));
        title.setText(order.getLabel());
        status.setText(lastTrack.getStatus());
        desc.setText(lastTrack.isRiding() ? lastTrack.getFrom()+" "+context.getString("to")+" "+lastTrack.getTo() : lastTrack.getFrom());

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
