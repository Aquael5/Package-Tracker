package br.nullexcept.package_tracker.widget;

import br.nullexcept.mux.app.Context;
import br.nullexcept.mux.graphics.Size;
import br.nullexcept.mux.res.AttributeList;
import br.nullexcept.mux.view.View;

public class TrackLine extends View {
    public TrackLine(Context context) {
        super(context);
    }

    public TrackLine(Context context, AttributeList attrs) {
        super(context, attrs);
    }

    @Override
    protected Size onMeasureContent(int parentWidth, int parentHeight) {
        Size size = super.onMeasureContent(parentWidth, parentHeight);
        size.height = parentHeight / 2;
        return size;
    }
}
