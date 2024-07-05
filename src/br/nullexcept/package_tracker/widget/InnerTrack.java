package br.nullexcept.package_tracker.widget;

import br.nullexcept.apis.tracker.tracking.Tracker;
import br.nullexcept.mux.app.Context;
import br.nullexcept.mux.view.View;
import br.nullexcept.mux.view.ViewGroup;
import br.nullexcept.mux.widget.ImageView;
import br.nullexcept.mux.widget.LinearLayout;
import br.nullexcept.mux.widget.TextView;

public class InnerTrack extends LinearLayout {
    public InnerTrack(Context context, Tracker tracker) {
        super(context);
        View inner = context.getLayoutInflater().inflate("");
        inner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addChild(inner);

        ImageView icon = findViewById("icon");
        TextView title = findViewById("status");
        TextView desc = findViewById("desc");
        TextView time = findViewById("time");

        desc.setText(tracker.isRiding() ? tracker.getFrom() + " PARA " + tracker.getTo() : tracker.getFrom());
    }
}
