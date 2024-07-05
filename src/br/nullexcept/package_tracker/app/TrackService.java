package br.nullexcept.package_tracker.app;

import br.nullexcept.mux.app.Service;
import br.nullexcept.package_tracker.log.Loggable;
import br.nullexcept.package_tracker.models.Order;
import br.nullexcept.package_tracker.models.OrderManager;
import br.nullexcept.package_tracker.shipper.TrackRes;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TrackService extends Service implements Loggable {
    private long lastChange = System.currentTimeMillis();
    private boolean loading = true;

    @Override
    protected void onCreate() {
        super.onCreate();
        //playAudio();
        refreshLoop();
    }

    private void refreshTrack() {
        boolean changed = false;
        for (Order order: OrderManager.getAll()) {
            loading = true;
            log("Updating track for: "+order.getCode()+" by "+order.getShipping().name());
            try {
                changed |= order.refresh();
            } catch (Exception e) {
                log("Error on refresh track");
                e.printStackTrace();
            }
        }
        loading = false;
        log("FINISHED TRACK UPDATE");
        if (changed) {
            playAudio();
            lastChange = System.currentTimeMillis();
        }
    }

    private void playAudio() {
        new Thread(()->{
            try {
                AudioInputStream in = AudioSystem.getAudioInputStream(TrackRes.getSoundFile());
                Clip clip = AudioSystem.getClip();
                clip.open(in);
                clip.start();
                log("AUDIO PLAYED");
            } catch (Exception e){}
        }).start();
    }

    public boolean isLoading() {
        return loading;
    }

    public void requestRefresh() {
        post(this::refreshTrack);
    }

    public long getLastChange() {
        return lastChange;
    }

    private void refreshLoop() {
        refreshTrack();
        postDelayed(this::refreshLoop, 1000*60*20); //Every 20 minutes
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
