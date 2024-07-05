package io.prototype.package_tracker.models;

import io.prototype.apis.tracker.correios.CorreiosTracker;
import io.prototype.apis.tracker.jadlog.JadLogTracker;
import br.nullexcept.koalas.KoalaArray;
import br.nullexcept.koalas.KoalaMap;
import io.prototype.apis.tracker.tracking.Tracker;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final ArrayList<Tracker> tracks = new ArrayList<>();
    private final Shipping ship;
    private final String code;
    private final String label;

    public Order(String label, String code, Shipping shipping) {
        this.ship = shipping;
        this.code = code;
        this.label = label;
    }

    public Order(KoalaMap data) {
        this(data.getString("label"), data.getString("code"), Shipping.valueOf(data.getString("shipping")));
        KoalaArray tracks = data.getArray("tracks");
        for (int i = 0; i < tracks.size(); i++) {
            KoalaMap item = tracks.getMap(i);
            this.tracks.add(new Tracker(item.getString("from"), item.getString("to"), item.getString("status"),
                    item.getString("date") + " " + item.getString("hour")));
        }
        order();
    }

    public long getLastChanged() {
        long time = 0;
        for (Tracker tracker: tracks) {
            time = Math.max(tracker.getTimestamp(), time);
        }
        return time;
    }


    public boolean refresh() {
        switch (ship) {
            case JADLOG:
                return apply(JadLogTracker.trackObject(code));
            case CORREIOS:
                return apply(CorreiosTracker.trackObject(code));
        }
        return false;
    }

    private boolean apply(List<Tracker> newTrackers) {
        boolean change = false;
        for (Tracker nw: newTrackers) {
            boolean find = false;
            for (Tracker trk: tracks) {
                if (trk.getTimestamp() == nw.getTimestamp()) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                change = true;
                this.tracks.add(nw);
            }
        }
        if (change) order();
        return change;
    }

    private void order() {
        tracks.sort((o1, o2) -> Long.compare(o1.getTimestamp(), o2.getTimestamp()));
    }

    public String getLabel() {
        return label;
    }

    public Shipping getShipping() {
        return ship;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return code+"/"+ship.name();
    }

    public KoalaMap serialize() {
        KoalaMap map = new KoalaMap();
        map.put("code", code);
        map.put("shipping", ship.name());
        map.put("label", label);
        KoalaArray tracks = new KoalaArray();
        order();
        for (Tracker tracker: this.tracks) {
            KoalaMap item = new KoalaMap();
            item.put("status", tracker.getStatus());
            item.put("from", tracker.getFrom());
            item.put("to", tracker.getTo());
            item.put("hour", tracker.getHour());
            item.put("date", tracker.getDate());
            tracks.add(item);
        }
        map.put("tracks", tracks);
        return map;
    }

    public int getTrackCount() {
        return tracks.size();
    }

    public List<Tracker> getTracks() {
        return new ArrayList<>(tracks);
    }
}