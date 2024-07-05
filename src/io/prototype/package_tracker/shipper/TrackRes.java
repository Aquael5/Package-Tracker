package io.prototype.package_tracker.shipper;

import io.prototype.package_tracker.models.Order;
import io.prototype.package_tracker.utils.FileUtils;
import br.nullexcept.apis.tracker.tracking.Tracker;
import br.nullexcept.mux.app.Context;
import br.nullexcept.mux.graphics.Drawable;
import br.nullexcept.mux.res.Resources;

import java.io.File;

public class TrackRes {
    static Drawable ICON_DONE;
    static Drawable ICON_MONEY;
    static Drawable ICON_UP;
    static Drawable ICON_SHIPPING;
    static Drawable ICON_INFO;
    static Drawable ICON_DOC;
    static File SOUND_FILE;

    public static void load(Context ctx) {
        Resources res = ctx.getResources();
        ICON_INFO = res.getDrawable("icon_notify");
        ICON_UP = res.getDrawable("icon_arrow_up");
        ICON_MONEY = res.getDrawable("icon_money");
        ICON_SHIPPING = res.getDrawable("icon_ship");
        ICON_DOC = res.getDrawable("icon_doc");
        ICON_DONE = res.getDrawable("icon_done");

        System.err.println(ICON_INFO);

        {
            File notifySound = new File(ctx.getFilesDir(), "sounds/notify.wav");
            if (!notifySound.exists()) {
                try {
                    FileUtils.writeData(notifySound, ctx.getResources().openRawStream("notify.wav"));
                } catch (Exception e){}
            }
            SOUND_FILE = notifySound;
        }
    }

    public static File getSoundFile() {
        return SOUND_FILE;
    }

    public static Drawable getIcon(Order order, Tracker track) {
        switch (order.getShipping()) {
            case CORREIOS:
                return parseCorreiosTrack(track);
        }
        return ICON_SHIPPING;
    }

    private static Drawable parseCorreiosTrack(Tracker track) {
        String status = track.getStatus().toUpperCase();

        if (status.contains("OBJETO POSTADO")) {
            return TrackRes.ICON_UP;
        } else if (status.contains("PAGAMENTO")) {
            return TrackRes.ICON_MONEY;
        } else if (track.isRiding()) {
            return TrackRes.ICON_SHIPPING;
        } else if (status.contains("INFORMAÇÕES") || status.contains("ANALISE")) {
            return TrackRes.ICON_DOC;
        } else if (status.contains("OBJETO ENTREGUE AO DESTINATÁRIO")) {
            return TrackRes.ICON_DONE;
        }

        return TrackRes.ICON_INFO;
    }
}
